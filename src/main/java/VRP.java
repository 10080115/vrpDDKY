import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liShiWei
 * @date 2020-03-12
 */
public class VRP {
    static String filePath = "/Users/li/Desktop/code/20160219/service/VRP/src/main/resources/jieguo.txt";
    public static void main(String[] args) {
        Map<Long, List<Point>> points = getVrpInput(filePath);

        //3.使用for循环利用EntrySet进行遍历
        for(Map.Entry<Long,List<Point>>entry: points.entrySet()){
            System.out.println("Item :"+entry.getKey()+" Count:"+entry.getValue().size());
        }
    }


    static Map<Long, List<Point>> getVrpInput(String filePath){
        Map<Long, List<Point>> batchId2Points = new HashMap<>();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(new File(filePath))));
            String lineTxt = br.readLine();
            while ((lineTxt = br.readLine()) != null) {
                String[] rawData = lineTxt.split("\t");

                Point p = new Point();
                Long batchNo = Long.parseLong(rawData[0]);
                p.setOrderId(Long.parseLong(rawData[2]));
                p.setDispatchUnixTime(Long.parseLong(rawData[3]));
                p.setStationId(Integer.parseInt(rawData[6]));
                p.setSendLatitude(Double.parseDouble(rawData[7]));
                p.setSendLongitude(Double.parseDouble(rawData[8]));
                p.setShippingType(Integer.parseInt(rawData[10]));
                p.setExpectArrivalUnixTime(Long.parseLong(rawData[14]));
                p.setAuditUnixTime(Long.parseLong(rawData[13]));

                //暂时先用默认值
                p.setOrderHang(false);
                p.setOrderHangSendUnixTime(1L);
                p.setFetchLatitude(39.924695);
                p.setFetchLongitude(116.421868);
                p.setEstimateUnixTime(1L);
                p.setSugUnixTime(1L);

                List<Point> points;
                if(batchId2Points.containsKey(batchNo)){
                    points = batchId2Points.get(batchNo);
                }else{
                    points = new ArrayList<>();
                }

                points.add(p);
                batchId2Points.put(batchNo, points);
            }
            br.close();
        } catch (Exception e) {
            System.err.println("read errors :" + e);
        }
        return batchId2Points;
    }
}
