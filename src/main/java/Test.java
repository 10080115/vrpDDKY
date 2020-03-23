//import util.Pointer;

import util.LatitudeLontitudeUtil;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Test {


    public static final int groupDelta = 1000;

    private List<Group> groupList = new ArrayList<>();//分组集合
    private Path finalRoute = new Path();


    //函数入口  依次插入各个分发点
    private void insertPointer( List<Point> points) {

        // TODO: 2020/3/23 排序 

//        for (int i = 0; i < points.size() - 1; i++) {//外层循环控制排序趟数
//            for (int j = 0; j < points.size() - 1 - i; j++) {//内层循环控制每一趟排序多少次
//                if (points.get(j).getExpectArrivalUnixTime() > points.get(j + 1).getExpectArrivalUnixTime()) {
//                    double temp = points.get(j).getExpectArrivalUnixTime();
//                    points.get(j).getExpectArrivalUnixTime() = points.get(j + 1).getExpectArrivalUnixTime();
//                    points.get(j + 1).getExpectArrivalUnixTime() = temp;
//                }
//            }
//        }
        
        for (int i = 0; i < points.size(); i++) {
            findGroupAndInsert2Which(points.get(i), i);
        }


    }

    /**
     * 为分发点找到分组 并将该点插入到其属于的分组
     *
     * @param point 分发点
     */
    private void findGroupAndInsert2Which(Point point, int index) {


        int size = groupList.size();
        int desOrderCount = finalRoute.pointList.size();//当前已经确认的订单序号数量
        if (index == 0) {//创建第一个分组
            Group group = new Group(size);
            group.insertOrder(point);
            groupList.add(group);
            finalRoute.addOrder(0, point);
        } else {//创建新的分组或者加入点到其他分组

            List<Set<Point>> map = PointUtils.cluster(finalRoute.pointList, 100);


            List<Group> belongGroups = new ArrayList<>();
            for (Group group : groupList) {
                int groupId = group.isBelongGroup(point);
                if (groupId > -1) belongGroups.add(group);
            }
            int groupSize = belongGroups.size();
            if (groupSize > 0) { //找到一个或者多个匹配到的分发组 将当前点插入到最后一个分发组内
                Group finalGroup = belongGroups.get(groupSize - 1);
                finalGroup.insertOrder(point);

                int groupId = finalGroup.id;
                if (groupId + 1 == groupList.size()) {//最後一個小组 只考虑组内排序 
                    int orderCount = finalGroup.pointList.size();
                    List<Path> paths = new ArrayList<>();
                    for (int i = 0; i < orderCount + 1; i++) {
                        Path path = new Path();
                        path.pointList.addAll(finalRoute.pointList);
                        path.pointList.add(desOrderCount - orderCount + i, point);
                        paths.add(path);
                    }
                    calcpoints(paths);
                    finalRoute = paths.get(paths.size() - 1); // TODO: 2020/3/22 计算paths 最优时间，确定desPath
                } else {//不是最后一个小组 考虑将其插入本小组 或者其后小组元素之后
                    List<Path> paths = new ArrayList<>();
                    for (int i = groupId; i < groupList.size(); i++) {
                        if (i == groupId) {
                            Group group = groupList.get(i);
                            for (int j = 0; j < group.pointList.size() + 1; j++) {
                                Path path = new Path();
                                path.pointList.addAll(finalRoute.pointList);
                                int groupOrdersCount = 0;
                                for (int k = groupId; k < groupList.size(); k++) {
                                    Group g = groupList.get(k);
                                    groupOrdersCount += g.pointList.size();
                                }

                                path.pointList.add(desOrderCount - groupOrdersCount + j, point);
                                paths.add(path);
                            }
                        } else {
                            for (int j = groupId + 1; j < groupSize; j++) {
                                int groupOrdersCount = 0;
                                for (int k = j + 1; k < groupSize; k++) {
                                    Group g = groupList.get(k);
                                    groupOrdersCount += g.pointList.size();
                                }
                                Path path = new Path();
                                path.pointList.addAll(finalRoute.pointList);
                                path.addOrder(desOrderCount - groupOrdersCount, point);
                                paths.add(path);
                            }
                        }
                    }
                    calcpoints(paths);
                    finalRoute = paths.get(paths.size() - 1);
//                    finalRoute = paths.get(0); // TODO: 2020/3/22 计算paths 最优时间，确定desPath

                }
            } else {//未找到匹配分发点的分组 则创建新分组
                finalRoute.pointList.add(point);
                Group group = new Group(size);
                group.insertOrder(point);
                groupList.add(group);
            }
        }
    }

    public static final double v = 10;

    private void calcpoints(List<Path> points) {
        for (Path path : points) {
            double getExpectArrivalUnixTime() = 0;
            double distance = 0;
            double time = 0;
            int i = 0;
            for (Point point : path.pointList) {
                double[] riderPosition;
                double[] orderPosition1;
                double[] orderPosition2;

                if (i == 0) {
                    riderPosition = LatitudeLontitudeUtil.lonLat2Mercator(point.getFetchLatitude(), point.getFetchLongitude());
                    double riderLongtitude = riderPosition[1];
                    double riderLatitude = riderPosition[0];

                    orderPosition1 = LatitudeLontitudeUtil.lonLat2Mercator(point.getSendLatitude(), point.getSendLongitude());
                    double orderLongtitude = orderPosition1[1];
                    double orderLatitude = orderPosition1[0];

                    //路径规划时间如何给出
                    time += Math.max(point.getEstimateUnixTime() - point.getExpectArrivalUnixTime(), 0);
                    distance += LatitudeLontitudeUtil.lineSpace(riderLongtitude, riderLatitude, orderLongtitude, orderLatitude);

                } else if (i < path.pointList.size()) {
                    orderPosition1 = LatitudeLontitudeUtil.lonLat2Mercator(path.pointList.get(i - 1).getSendLatitude(), path.pointList.get(i - 1).getSendLongitude());
                    double order1Longtitude = orderPosition1[1];
                    double order1Latitude = orderPosition1[0];

                    orderPosition2 = LatitudeLontitudeUtil.lonLat2Mercator(path.pointList.get(i).getSendLatitude(), path.pointList.get(i).getSendLongitude());
                    double order2Longtitude = orderPosition2[1];
                    double order2latitude = orderPosition2[0];

                    time += Math.max(path.pointList.get(i).getEstimateUnixTime() - path.pointList.get(i).getExpectArrivalUnixTime(), 0);
                    distance += LatitudeLontitudeUtil.lineSpace(order1Longtitude, order1Latitude, order2Longtitude, order2latitude);
                }

                i++;
            }
            getExpectArrivalUnixTime() = time + distance;
            path.getExpectArrivalUnixTime() = getExpectArrivalUnixTime();
            path.distance = distance;
        }

        //
        for (int i = 0; i < points.size() - 1; i++) {//外层循环控制排序趟数
            for (int j = 0; j < points.size() - 1 - i; j++) {//内层循环控制每一趟排序多少次
                if (points.get(j).getExpectArrivalUnixTime() > points.get(j + 1).getExpectArrivalUnixTime()) {
                    double temp = points.get(j).getExpectArrivalUnixTime();
                    points.get(j).getExpectArrivalUnixTime() = points.get(j + 1).getExpectArrivalUnixTime();
                    points.get(j + 1).getExpectArrivalUnixTime() = temp;
                }
            }
        }

        //
    }

    
    /**
     * 输出最终顺序
     */
    private void printOrderList() {
        List<Point> pointList = new ArrayList<>();
        for (Group group : groupList) {
            pointList.addAll(group.pointList);
        }

    }


    private class Group {
        private int id;

        public Group(int id) {
            this.id = id;
        }

        private Group(String name) {
            this.name = name;
        }

        private String name;
        private List<Point> pointList = new ArrayList<>();

        private int isBelongGroup(Point point) {
            // TODO: 2020/3/21 实现是否属于该组的方法 返回属于组的下标
            return -1;
        }

        /**
         * 插入第n（n>1）个分发点
         *
         * @param point 分发点
         */
        private void insertOrder(Point point) {
            pointList.add(point);
        }
    }

    private class Path {

        double getExpectArrivalUnixTime() = 0;
        double distance = 0;
        double time = 0;
        private List<Point> pointList = new ArrayList<>();

        public void addOrder(int index, Point point) {
            pointList.add(index, point);
        }
    }


}
