package src.yugui.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.yugui.mapper.ValveReportMapper;
import src.yugui.service.ValveReportService;
import src.yugui.entity.ValveReportInfo;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Description:
 * @Author: XiaoPanPan
 * @Date: 2019/8/8 14:48
 */
@Service(value = "valveReport")
public class ValveReportServiceImpl implements ValveReportService {

    @Resource(name = "valveReportMapper")
    private ValveReportMapper valveReportMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insertValveReportInfo(Map<String, Object> valveMap) {
        return valveReportMapper.creatReport(valveMap);
    }

    @Override
    public ValveReportInfo getValveReportByReportNo(String reportNo) {
        return valveReportMapper.getValveReportByReportNo(reportNo);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateValveReportInfo(Map<String, Object> valveMap) {
        return valveReportMapper.updateValveReportInfo(valveMap);
    }
}
