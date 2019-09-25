package src.yugui.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import src.yugui.common.ResponseMsg;
import src.yugui.common.TimeTool;
import src.yugui.common.constant.CheckResult;
import src.yugui.entity.Company;
import src.yugui.entity.UserInfo;
import src.yugui.entity.ValveReportInfo;
import src.yugui.entity.VerificationResult;
import src.yugui.service.CompanyService;
import src.yugui.service.RecordService;
import src.yugui.service.ValveHistoryService;
import src.yugui.service.ValveReportService;
import src.yugui.util.Constant;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * @Description: 生成新建报告控制层
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 10:27
 */
@Api(tags = "报告管理层", description = "ValveReportController")
@RestController
@RequestMapping("report")
@Validated
public class ValveReportController extends BaseController {
    private static final Logger logger = Logger.getLogger(ValveReportController.class);

    @Resource(name = "valveReport")
    private ValveReportService valveReportService;

    @Resource(name = "valveHistory")
    private ValveHistoryService valveHistoryService;

    @Resource(name = "companyService")
    private CompanyService companyService;

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "新建报告编号自增接口", response = ResponseMsg.class)
    @RequestMapping(value = "createReportNumber", method = {RequestMethod.GET})
    public ResponseMsg createNewValve() {
        String olderReportNumber = valveHistoryService.getReportNo();
        logger.info("-----olderReportNumber旧编号:---->" + olderReportNumber);
        String newReportNumber = getNewReportNumber(olderReportNumber);
        logger.info("-----newReportNumber新编号:------>" + newReportNumber);
        return ResponseMsg.ok(newReportNumber);
    }

    @ApiOperation(value = "创建新报告接口", response = ResponseMsg.class)
    @RequestMapping(value = "createValve", method = {RequestMethod.POST})
    public ResponseMsg createValve(@RequestBody(required = true) Map<String, Object> valveMap) {
        UserInfo userInfo = getLoginUser();
        logger.info("valveMap--->" + valveMap);
        String reportNo = valveMap.get("reportNo").toString();
        //校验报告编号是否已经存在
        ValveReportInfo valveReportInfo = valveReportService.getValveReportByReportNo(reportNo);
        if (valveReportInfo != null) {
            return ResponseMsg.error("编号已存在, 请重新刷新页面! ");
        }

        //校验参数
        logger.info("valveMap" + valveMap);
        Map<String, Object> newValveMap = checkParm(valveMap);
        logger.info("newValveMap" + newValveMap);
        valveReportService.insertValveReportInfo(newValveMap);

        //创建新报告的同时向报告历史记录表添加记录数据
        String tsStr = TimeTool.getCurrentTime();
        valveMap.put("modifyFlag", 0);
        valveMap.put("createName", userInfo.getUserName());
        valveMap.put("createRealName", userInfo.getRealName());
        valveMap.put("createTime", tsStr);
        valveHistoryService.addValvehistory(valveMap);

        //创建新报告的同时向company表也插入一条数据,以便下次新建报告输入公司名字自动显示公司其他信息
        Map<String, Object> companyMap = new HashMap<>();
        String companyUse = (String) valveMap.get("companyUse");
        //判断企业是否已经存在
        Company companyList = companyService.getCompanyUseListByCompanyUse(companyUse);
        if (companyList == null) {//不存在则添加到企业列表
            companyMap.put("createTime", tsStr);
            companyMap.put("enable", 1);
            companyMap.put("companyUse", companyUse);
            companyMap.put("companyAddress", valveMap.get("companyAddress"));
            companyMap.put("companyContacts", valveMap.get("companyContacts"));
            companyMap.put("telephone", valveMap.get("telephone"));
            companyService.addCompany(companyMap);
        }
        logger.info("新建报告成功!");
        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("reportNo", reportNo);
        recordMap.put("userName", userInfo.getUserName());
        String message = "@{userName}新建了一份报告: @{reportNo}查看报告详情";
        recordMap.put("message", message);
        recordMap.put("operationTime", nowTime);
        recordMap.put("operationType", Constant.OPERATION_TYPE_NOTIFY);
        recordService.addRecord(recordMap);

        return ResponseMsg.ok(reportNo);
    }

    @ApiOperation(value = "修改报告接口", response = ResponseMsg.class)
    @RequestMapping(value = "updateReport", method = {RequestMethod.POST})
    public ResponseMsg updateReport(@RequestBody(required = true) Map<String, Object> valveMap) {
        UserInfo userInfo = getLoginUser();
        String tsStr = TimeTool.getCurrentTime();

        Object reportNo = valveMap.get("reportNo");
        if (reportNo == null || reportNo == "") {
            return ResponseMsg.error("未提交报告编号");
        }
        //校验参数
        logger.info("valveMap:" + valveMap);
        Map<String, Object> newValveMap = updateParm(valveMap);
        logger.info("newValveMap:" + newValveMap);
        Boolean upReport = valveReportService.updateValveReportInfo(newValveMap);
        if (!upReport) {
            return ResponseMsg.error("修改报告失败！");
        }
        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("operationTime", nowTime);
        recordMap.put("reportNo", reportNo);
        recordMap.put("userName", userInfo.getUserName());
        String message = "@{userName}修改了报告: @{reportNo}";
        recordMap.put("message", message);
        recordMap.put("operationType", Constant.OPERATION_TYPE_NOTIFY);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok("修改成功！");
    }

    @ApiOperation(value = "自动检测报告校验结论是否合格接口", response = ResponseMsg.class)
    @RequestMapping(value = "autoCheck", method = {RequestMethod.POST})
    public ResponseMsg autoCheck(@RequestBody(required = true) Map<String, String> valveMap) {
        //要求整定压力
        String requireSettingPressure = valveMap.get("requireSettingPressure");

        //第三次试验实际整定压力
        String thirdSettingPressure = valveMap.get("thirdSettingPressure");

        if (StringUtils.isEmpty(requireSettingPressure) || StringUtils.isEmpty(thirdSettingPressure)) {
            return ResponseMsg.error("未检测到压力值");
        }

        Double requireSettingPressureNum = Double.valueOf(requireSettingPressure);
        Double thirdSettingPressureNum = Double.valueOf(thirdSettingPressure);
        Double different = requireSettingPressureNum - thirdSettingPressureNum;
        VerificationResult verificationResult = new VerificationResult();
        //按安全阀检测国标检测标准
        if (requireSettingPressureNum <= 0.5) {
            if (different >= -0.015 && different <= 0.015) {
                verificationResult.setIsQualified(CheckResult.CHECK_RESULT_ONE);
                verificationResult.setReason(CheckResult.CHECK_QUALIFIED_REASON);
                return ResponseMsg.ok(verificationResult);
            } else {
                verificationResult.setIsQualified(CheckResult.CHECK_RESULT_TWO);
                verificationResult.setReason(CheckResult.CHECK_UNQUALIFIED_LESS);
                return ResponseMsg.ok(verificationResult);
            }
        } else {
            if (different / requireSettingPressureNum >= -0.03 && different / requireSettingPressureNum <= 0.03) {
                verificationResult.setIsQualified(CheckResult.CHECK_RESULT_ONE);
                verificationResult.setReason(CheckResult.CHECK_QUALIFIED_REASON);
                return ResponseMsg.ok(verificationResult);
            }
            verificationResult.setIsQualified(CheckResult.CHECK_RESULT_TWO);
            verificationResult.setReason(CheckResult.CHECK_UNQUALIFIED_GREATER);
            return ResponseMsg.ok(verificationResult);
        }
    }

}
