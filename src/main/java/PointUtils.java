
import java.util.*;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import util.Util;


/**
     * 地点工具类
     * @author zjh
     * @date 2020-1-13 15:32
     */
public class PointUtils {

//    private static final Logger logger = LoggerFactory.getLogger(Tmp.PointUtils.class);


    public static void main(String[] args) {

////        String ymd = DateUtils.formatYMD(new Date());
////        System.out.println(ymd);
////        Date date = DateUtils.parseYMD(ymd);
////        System.out.println(DateUtils.getSubMinute(date,new Date())/5);
        Point p1 = new Point(1L,116.422594,39.930595);
        Point p2 = new Point(2L,116.426403,39.93148);
        Point p3 = new Point(3L,116.420079,39.929211);
        Point p4 = new Point(4L,116.447316,39.921327);
        Point p5 = new Point(5L,116.446956,39.923153);
        Point p6 = new Point(6L,116.445986,39.920995);
        Point p7 = new Point(7L,116.43341,39.912666);
        Point p8 = new Point(8L,116.433589,39.911089);
        Point p9 = new Point(9L,116.431793,39.913302);

        List<Point> pointList  = Lists.newArrayList(p1,p2,p3,p4,p5,p6,p7,p8,p9);
//        System.out.println(pointList);
        List<Set<Point>> list = cluster(pointList, 1000);
        list.forEach(set ->{
            set.forEach(point ->{
                System.out.print(point.getOrderId()+",");
            });
            System.out.println();
        });

    }


    /**
     * 聚类方法
     * @param pointList 点集合
     * @param distance 聚类距离
     */
    public static List<Set<Point>> cluster(List<Point> pointList,int distance){
        Long[] centerIds = new Long[pointList.size()];      //中心点的ID，得到null数组，centerIds=[null,null,null....]
        Integer[] distance2center = new Integer[pointList.size()];      //到中心点的距离，得到null数组，distance2center=[null,null,null...]
        for(int i = 0;i < pointList.size();i++){        //循环获取点集合中的点
            Point point = pointList.get(i);     //循环获取点集合中的点point，作为中心点
            if(distance2center[i] == null){
                List<Point> neighbourPoint = getNeighbourPoint(pointList, point, distance);     //neighbourPoint是邻近列表，该表是点point的邻近列表
                centerIds[i] = point.getOrderId();
//                centerIds[i] = point.getOrderId();       //在第i个位置占上中心点的ID
                distance2center[i] = 0;     //中心点位置上为0
                if(neighbourPoint.size() > 0){      //如果中心点有邻近点，即构成了邻近表，该邻近列表大小大于0
                    for(Point neighbour:neighbourPoint){        //循环邻近列表中的邻近点
//                        int minDistance = MapUtils.getDistanceValue(point, neighbour, RouteMatrixEnum.STRAIGHT,1.4);        //计算中心点和邻近点的距离
                        int minDistance = (int)Util.lngLat2Distance(point.getSendLatitude(),point.getSendLongitude(),neighbour.getSendLatitude(),neighbour.getSendLongitude());
                        int idx = pointList.indexOf(neighbour);     //把邻近点在点集合中的index赋给idx
                        if(idx != -1){      //判断idx是否等于-1
                            if(distance2center[idx] == null || minDistance < distance2center[idx]){     //
                                centerIds[idx] = point.getOrderId();
//                                centerIds[idx] = point.getOrderId();     //把中心点的ID赋给邻近点的位置
                                distance2center[idx] = minDistance;
                            }
                        }
                    }
                }
            }
        }
        Map<Long,Set<Point>> map = Maps.newHashMap();
        for(int i = 0;i < pointList.size();i++) {
            Point point = pointList.get(i);
            Long id = centerIds[i];
            if(map.containsKey(id)) {
                map.get(id).add(point);
            }else {
                Set<Point> set = Sets.newHashSet();
                set.add(point);
                map.put(id, set);
            }
        }
        List<Set<Point>> returnList = new ArrayList<>(map.values());
        return returnList;
    }

    /**
     * 获取邻近点
     * @param pointList 点集合
     * @param point 中心点
     * @param distance 距离阈值
     */
    private static List<Point> getNeighbourPoint(List<Point> pointList,Point point ,int distance){
        List<Point> list = Lists.newArrayList();        //空的列表[],list=[]
        for(Point p: pointList) {
            if(point.getOrderId().equals(p.getOrderId())){        //判断中心点point的ID是否和点集合中p的ID相等
                continue;       //相等的话，就继续循环点集合中的点
            }
//            if(point.getOrderId().equals(p.getOrderId())){
//                continue;
//            }
//            int value = MapUtils.getDistanceValue(point, p, RouteMatrixEnum.STRAIGHT, 1.4);     //不等的话，计算中心点point和p之间的距离
            int value = (int)Util.lngLat2Distance(point.getSendLatitude(),point.getSendLongitude(),p.getSendLatitude(),p.getSendLongitude());
            System.out.println(value);
            if(value <= distance) {
                list.add(p);
            }
        }
        return list;
    }

}
