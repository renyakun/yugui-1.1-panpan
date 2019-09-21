package src.yugui.service;

import src.yugui.entity.ValveReportInfo;

import java.util.Map;

/**
 * @Description: ValveReportService接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/8 14:35
 */
public interface ValveReportService {

    /**
     * 生成报告
     */
    Boolean insertValveReportInfo(Map<String, Object> valveMap);

    /**
     * 根据编号查询 得到报告表详情
     */
    ValveReportInfo getValveReportByReportNo(String reportNo);

    /**
     * 修改报告
     */
    Boolean updateValveReportInfo(Map<String, Object> valveMap);
}
