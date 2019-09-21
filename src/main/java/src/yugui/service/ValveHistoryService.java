package src.yugui.service;

import src.yugui.entity.ValveHistoryInfo;
import src.yugui.entity.ValveHistoryNotify;

import java.util.List;
import java.util.Map;

/**
 * @Description: ValveHistoryService接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:19
 */

public interface ValveHistoryService {
    /**
     * 获取所有ValveHistory表信息
     */
    List<ValveHistoryInfo> getValveHistoryList(Map<String, Object> valveHistoryMap);

    /**
     * 向ValveHistory表插入新生成报告信息
     */
    Boolean addValvehistory(Map<String, Object> valveMap);

    /**
     * 修改ValveHistory表信息
     */
    Boolean updateValvehistory(String reportNo, Integer flag);

    /**
     * 修改ValveHistory表信息
     */
    Boolean updateValvehistory(Map<String, String> infoMap);

    /**
     * 根据编号查询
     */
    ValveHistoryInfo getValveHistoryInfoByReportNo(String reportNo);

    /**
     * 获取所有ValveHistory表最后一条数据的reportNo字段
     */
    String getReportNo();

    /**
     * 获取今日内所有ValveHistory表信息通过状态
     */
    List<ValveHistoryNotify> getReportNumByModiflyFlagAndTime(List<Integer> modiflyFlags, String startTime, String nowTime);


}
