package util;

import java.util.List;

/**
 * @author guoyang
 * @date 2020-03-16
 */

public class Util {

    /**
     * 两经纬度转距离（米）
     * m
     */
    private static double earthRadius = 6378.137;
    private static double rad(double d){
        return d*Math.PI/180.0;
    }
    public static double lngLat2Distance(double lat1,double lng1,double lat2,double lng2){
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = rad(lat1)-rad(lat2);
        double b = rad(lng1)-rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2)
                + Math.cos(radLat1) * Math.cos(radLat2)
                * Math.pow(Math.sin(b / 2), 2)));
        s = s*earthRadius;
        s = Math.round(s*10000d)/10000d;
        s = s*1000;
        return s; //返回值单位为米
    }
    public static void main(String[] args) {
        double distance = lngLat2Distance(39.9322187,116.4256897,
                39.915081, 116.439973);
        System.out.println("距离" + distance + "米");
    }




}
