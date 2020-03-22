//import util.Pointer;

import java.util.ArrayList;
import java.util.List;

public class Test {


    public static final int groupDelta = 1000;

    private List<Group> groupList = new ArrayList<>();//分组集合
    private Path finalRoute = new Path();


    //函数入口  依次插入各个分发点
    private void insertPointer(Order order) {
//        findGroupAndInsert2Which(order);
    }


    /**
     * 为分发点找到分组 并将该点插入到其属于的分组
     *
     * @param order 分发点
     */
    private void findGroupAndInsert2Which(Order order, int index) {
        int size = groupList.size();
        int desOrderCount = finalRoute.orderList.size();//当前已经确认的订单序号数量
        if (index == 0) {//创建第一个分组
            Group group = new Group(size);
            group.insertOrder(order);
            groupList.add(group);
            finalRoute.addOrder(0, order);
        } else {//创建新的分组或者加入点到其他分组
            List<Group> belongGroups = new ArrayList<>();
            for (Group group : groupList) {
                int groupId = group.isBelongGroup(order);
                if (groupId > -1) belongGroups.add(group);
            }
            int groupSize = belongGroups.size();
            if (groupSize > 0) { //找到一个或者多个匹配到的分发组 将当前点插入到最后一个分发组内
                Group finalGroup = belongGroups.get(groupSize - 1);
                finalGroup.insertOrder(order);
                // TODO: 2020/3/22 按顺序插入到分组

                int groupId = finalGroup.id;
                if (groupId + 1 == groupList.size()) {//最後一個小组 只考虑组内排序 
                    int orderCount = finalGroup.orderList.size();
                    List<Path> paths = new ArrayList<>();
                    for (int i = 0; i < orderCount + 1; i++) {
                        Path path = new Path();
                        path.orderList.addAll(finalRoute.orderList);
                        path.orderList.add(desOrderCount - orderCount + i, order);
                        paths.add(path);
                    }
                    finalRoute = paths.get(0); // TODO: 2020/3/22 计算paths 最优时间，确定desPath
                } else {//不是最后一个小组 考虑将其插入本小组 或者其后小组元素之后
                    List<Path> paths = new ArrayList<>();
                    for (int i = groupId; i < groupList.size(); i++) {
                        if (i == groupId) {
                            Group group = groupList.get(i);
                            for (int j = 0; j < group.orderList.size() + 1; j++) {
                                Path path = new Path();
                                path.orderList.addAll(finalRoute.orderList);
                                int groupOrdersCount = 0;
                                for (int k = groupId; k < groupList.size(); k++) {
                                    Group g = groupList.get(k);
                                    groupOrdersCount += g.orderList.size();
                                }

                                path.orderList.add(desOrderCount - groupOrdersCount + j, order);
                                paths.add(path);
                            }
                        } else {
                            for (int j = groupId + 1; j < groupSize; j++) {
                                int groupOrdersCount = 0;
                                for (int k = j + 1; k < groupSize; k++) {
                                    Group g = groupList.get(k);
                                    groupOrdersCount += g.orderList.size();
                                }
                                Path path = new Path();
                                path.orderList.addAll(finalRoute.orderList);
                                path.addOrder(desOrderCount - groupOrdersCount, order);
                                paths.add(path);
                            }
                        }
                    }
                    finalRoute = paths.get(0); // TODO: 2020/3/22 计算paths 最优时间，确定desPath

                }
            } else {//未找到匹配分发点的分组 则创建新分组
                finalRoute.orderList.add(order);
                Group group = new Group(size);
                group.insertOrder(order);
                groupList.add(group);
            }
        }
    }


    private void calcPathList() {

    }

    /**
     * 输出最终顺序
     */
    private void printOrderList() {
        List<Order> orderList = new ArrayList<>();
        for (Group group : groupList) {
            orderList.addAll(group.orderList);
        }
        for (Order order : orderList) {
            System.out.println(order.name);
        }
    }

    private class Order {
        private int id;
        public String name;
        public int x;
        public int y;
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
        private List<Order> orderList = new ArrayList<>();

        private int isBelongGroup(Order order) {
            // TODO: 2020/3/21 实现是否属于该组的方法 返回属于组的下标
            return -1;
        }

        /**
         * 插入第n（n>1）个分发点
         *
         * @param order 分发点
         */
        private void insertOrder(Order order) {
            orderList.add(order);
        }
    }

    private class Path {
        private List<Order> orderList = new ArrayList<>();

        public void addOrder(int index, Order order) {
            orderList.add(index, order);
        }
    }


}
