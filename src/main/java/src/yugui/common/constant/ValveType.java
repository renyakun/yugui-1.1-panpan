package src.yugui.common.constant;

import java.util.Map;

/**
 * @Description: 生成报告表的安全阀类型
 * @Author: XiaoPanPan
 * @Date: 2019/8/7 13:57
 */
public class ValveType {
    private static final String SPRING_DIRECT_LOAD = "弹簧直载式";
    private static final String PILOT_TYPE = "先导式";
    private static final String NET_WEIGHT_TYPE = "净重式";
    private static final String LEVER_TYPE = "杠杆式";

    //将参数valveType转成所对应的值
    public static void getType(String safetyValveType, Map<String, Object> valveMap) {
        if (safetyValveType.equals("1")) {
            valveMap.put("valveType", ValveType.SPRING_DIRECT_LOAD);
        }
        if (safetyValveType.equals("2")) {
            valveMap.put("valveType", ValveType.PILOT_TYPE);
        }
        if (safetyValveType.equals("3")) {
            valveMap.put("valveType", ValveType.NET_WEIGHT_TYPE);
        }
        if (safetyValveType.equals("4")) {
            valveMap.put("valveType", ValveType.LEVER_TYPE);
        }
    }
}
