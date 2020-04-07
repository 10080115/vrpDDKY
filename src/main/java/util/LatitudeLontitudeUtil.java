package util;

/**
 * @author guoyang
 * @date 2020-03-18
 */

//球面距离（米）
public class LatitudeLontitudeUtil {

    private static final double EARTH_RADIUS = 6371393;
    static double M_PI = Math.PI;

    public LatitudeLontitudeUtil() {

    }

    //经纬度转墨卡托
    // 经度(lon)，纬度(lat)
    public static double[] lonLat2Mercator(double lon,double lat)
    {
        double[] xy = new double[2];
        double x = lon *20037508.342789/180;
        double y = Math.log(Math.tan((90+lat)*M_PI/360))/(M_PI/180);
        y = y *20037508.34789/180;
        xy[0] = x;
        xy[1] = y;
        return xy;
    }

    // 计算两点之间的距离(米)
    public static double lineSpace(double x1, double y1, double x2, double y2) {
        double lineLength = 0;
        lineLength = Math.sqrt((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2));
        return lineLength;
    }


    public static void main(String[] args) {

        //点到直线的距离算法测试

        double x1 = 39.930716700000005;
        double y1 = 116.4452682;

        double x2 = 39.924695;
        double y2 = 116.421868;

        double[] xytemp = new double[2];

        xytemp = LatitudeLontitudeUtil.lonLat2Mercator(y1, x1);
        double x1m = xytemp[1];
        double y1m = xytemp[0];

        xytemp = LatitudeLontitudeUtil.lonLat2Mercator(y2, x2);
        double x2m = xytemp[1];
        double y2m = xytemp[0];

        double d =LatitudeLontitudeUtil.lineSpace(x1m, y1m, x2m, y2m);

        System.out.println(d);

    }
}