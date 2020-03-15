import java.util.List;

/**
 * @author liShiWei
 * @date 2020-03-12
 */
class Score {
    /**
     * 路径总分
     * @param points 排好顺序的任务点
     * @return
     */
    public double getScore(List<Point> points){
        return getDistanceSocre(points) + getTimeScore(points);
    }

    /**
     * 路成分
     * @param points
     * @return
     */
    private double getDistanceSocre(List<Point> points){
        return 0;
    }

    /**
     * 时间分
     * @param points
     * @return
     */
    private double getTimeScore(List<Point> points){
        return 0;
    }
}
