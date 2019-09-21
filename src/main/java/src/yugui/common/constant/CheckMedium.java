package src.yugui.common.constant;

import java.util.Map;

/**
 * @Description: 生成报告表的校验介质
 * @Author: XiaoPanPan
 * @Date: 2019/8/8 10:52
 */
public class CheckMedium {
    private static final String CHECK_MEDIUM_ONE = "水";
    private static final String CHECK_MEDIUM_TWO = "压缩空气";
    private static final String CHECK_MEDIUM_THR = "氮气";
    private static final String CHECK_MEDIUM_FOU = "蒸汽";
    private static final String CHECK_MEDIUM_FIV = "液压油";

    //将参数checkMedium转成所对应的值
    public static void getType(String checkMedium, Map<String, Object> valveMap) {
        if (checkMedium.equals("1")) {
            valveMap.put("checkMedium", CheckMedium.CHECK_MEDIUM_ONE);
        }
        if (checkMedium.equals("2")) {
            valveMap.put("checkMedium", CheckMedium.CHECK_MEDIUM_TWO);
        }
        if (checkMedium.equals("3")) {
            valveMap.put("checkMedium", CheckMedium.CHECK_MEDIUM_THR);
        }
        if (checkMedium.equals("4")) {
            valveMap.put("checkMedium", CheckMedium.CHECK_MEDIUM_FOU);
        }
        if (checkMedium.equals("5")) {
            valveMap.put("checkMedium", CheckMedium.CHECK_MEDIUM_FIV);
        }
    }
}
