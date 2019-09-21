package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import src.yugui.common.BaseMapper;
import src.yugui.entity.ValveReportInfo;

import java.util.Map;

/**
 * @Description:  mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Repository(value = "valveReportMapper")
public interface ValveReportMapper extends BaseMapper<ValveReportInfo>{

    Boolean creatReport(Map<String, Object> valveMap);

    ValveReportInfo getValveReportByReportNo(@Param("reportNo") String reportNo);

    Boolean updateValveReportInfo(Map<String, Object> valveMap);

}
