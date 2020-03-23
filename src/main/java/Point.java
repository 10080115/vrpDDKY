/**
 * @author liShiWei
 * @date 2020-03-12
 */
public class Point {
    /**
     * 站点编号
     */
    private Integer stationId;

    /**
     * 取点纬度
     */
    private Double fetchLatitude;

    /**
     * 取点经度
     */
    private Double fetchLongitude;

    /**
     * 运单编号
     */
    private Long orderId;

    /**
     * 送点纬度
     */
    private Double sendLatitude;

    /**
     * 送点经度
     */
    private Double sendLongitude;

    /**
     * 运单类型
     */
    private Integer shippingType;

    /**
     * 审核时间
     */
    private Long auditUnixTime;

    /**
     * 考核时间
     */
    private Long expectArrivalUnixTime;

    private Long arriveUnixTime;


    /**
     * 预估到达时间(路径规划给出)
     */
    private Long estimateUnixTime;

    /**
     * 是否为再投运单
     */
    private boolean orderHang;

    /**
     * 再投运单约定配送时间
     */
    private Long orderHangSendUnixTime;

    /**
     * 指派时刻
     */
    private Long dispatchUnixTime;

    /**
     * 运单交付时间
     */
    private Long sugUnixTime;

//    /**
//     * @author guoyang
//     * @param l
//     * @param v
//     * @param v1
//     */
//
//    public Point(long l, double v, double v1) {
//    }


    public Point() {
    }

    public Point(Long orderId, Double sendLatitude, Double sendLongitude) {
        this.orderId = orderId;
        this.sendLatitude = sendLatitude;
        this.sendLongitude = sendLongitude;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Double getSendLatitude() {
        return sendLatitude;
    }

    public void setSendLatitude(Double sendLatitude) {
        this.sendLatitude = sendLatitude;
    }

    public Double getSendLongitude() {
        return sendLongitude;
    }

    public void setSendLongitude(Double sendLongitude) {
        this.sendLongitude = sendLongitude;
    }

    public Integer getShippingType() {
        return shippingType;
    }

    public void setShippingType(Integer shippingType) {
        this.shippingType = shippingType;
    }

    public Long getAuditUnixTime() {
        return auditUnixTime;
    }

    public void setAuditUnixTime(Long auditUnixTime) {
        this.auditUnixTime = auditUnixTime;
    }

    public Long getExpectArrivalUnixTime() {
        return expectArrivalUnixTime;
    }

    public void setExpectArrivalUnixTime(Long expectArrivalUnixTime) {
        this.expectArrivalUnixTime = expectArrivalUnixTime;
    }
    public Long getArriveUnixTime() {
        return arriveUnixTime;
    }

    public void setArriveUnixTime(Long arriveUnixTime) {
        this.arriveUnixTime = arriveUnixTime;
    }

    public Long getEstimateUnixTime() {
        return estimateUnixTime;
    }

    public void setEstimateUnixTime(Long estimateUnixTime) {
        this.estimateUnixTime = estimateUnixTime;
    }

    public Long getOrderHangSendUnixTime() {
        return orderHangSendUnixTime;
    }

    public void setOrderHangSendUnixTime(Long orderHangSendUnixTime) {
        this.orderHangSendUnixTime = orderHangSendUnixTime;
    }

    public boolean isOrderHang() {
        return orderHang;
    }

    public void setOrderHang(boolean orderHang) {
        this.orderHang = orderHang;
    }

    public Integer getStationId() {
        return stationId;
    }

    public void setStationId(Integer stationId) {
        this.stationId = stationId;
    }

    public Double getFetchLatitude() {
        return fetchLatitude;
    }

    public void setFetchLatitude(Double fetchLatitude) {
        this.fetchLatitude = fetchLatitude;
    }

    public Double getFetchLongitude() {
        return fetchLongitude;
    }

    public void setFetchLongitude(Double fetchLongitude) {
        this.fetchLongitude = fetchLongitude;
    }

    public Long getDispatchUnixTime() {
        return dispatchUnixTime;
    }

    public void setDispatchUnixTime(Long dispatchUnixTime) {
        this.dispatchUnixTime = dispatchUnixTime;
    }

    public Long getSugUnixTime() {
        return sugUnixTime;
    }

    public void setSugUnixTime(Long sugUnixTime) {
        this.sugUnixTime = sugUnixTime;
    }
}
