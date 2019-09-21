package src.yugui.common.constant;

import java.util.Map;

/**
 * @Description: 生成报告表的校验结果
 * @Author: XiaoPanPan
 * @Date: 2019/8/8 11:20
 */
public class CheckResult {
    public static final String CHECK_RESULT_ONE = "合格";
    public static final String CHECK_RESULT_TWO = "不合格";

    public static final String CHECK_QUALIFIED_REASON = " 据《安全阀安全技术监察规程》已自动检测出结果为合格，如若校验\"锅炉\"类安全阀，请自行计算检验结论";
    public static final String CHECK_UNQUALIFIED_LESS = " 据《安全阀安全技术监察规程》已自动检测出结果，原因：要求整定压力值小于或等于0.5 Mpa时，第三次试验实际整定压力值与要求整定值的允许误差为±0.015MPa，当前已超出范围视为不合格，如若校验\"锅炉\"类安全阀，请自行计算检验结论";
    public static final String CHECK_UNQUALIFIED_GREATER = " 据《安全阀安全技术监察规程》已自动检测出结果，原因：要求整定压力值大于0.5 MPa 时，允许第三次试验实际整定压力值与要求整定值误差为±3%，当前已超出范围视为不合格，如若校验\"锅炉\"类安全阀，请自行计算检验结论";

    //将参数checkResult转成所对应的值
    public static void getType(String checkResult, Map<String, Object> valveMap) {
        if (checkResult.equals("1")) {
            valveMap.put("checkResult", CheckResult.CHECK_RESULT_ONE);
        }
        if (checkResult.equals("2")) {
            valveMap.put("checkResult", CheckResult.CHECK_RESULT_TWO);
        }
    }
}
