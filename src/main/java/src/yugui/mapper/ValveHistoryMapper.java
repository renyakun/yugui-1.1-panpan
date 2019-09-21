package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import src.yugui.common.BaseMapper;
import src.yugui.entity.ValveHistoryInfo;
import src.yugui.entity.ValveHistoryNotify;

import java.util.List;
import java.util.Map;

/**
 * @Description: mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Component
@Repository(value = "valveHistoryMapper")
public interface ValveHistoryMapper extends BaseMapper<ValveHistoryInfo> {

    List<ValveHistoryInfo> getValHistoryList(Map<String, Object> valveHistoryMap);

    Boolean insertValvehistory(Map<String, Object> valveMap);

    Boolean updateValvehistory(@Param("reportNo") String reportNo, @Param("flag") Integer flag);

    Boolean updateValvehistoryByReport(Map<String, String> infoMap);

    ValveHistoryInfo getValveHistoryInfoByReportNo(@Param("reportNo") String reportNo);

    String getReportNo();

    List<ValveHistoryNotify> getReportNumByModiflyFlagAndTime(@Param("modiflyFlags") List<Integer> modiflyFlags, @Param("startTime") String startTime, @Param("nowTime") String nowTime);
}
