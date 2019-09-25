package src.yugui.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import src.yugui.common.ResponseMsg;
import src.yugui.common.TimeTool;
import src.yugui.common.constant.Position;
import src.yugui.service.*;
import src.yugui.entity.UserInfo;
import src.yugui.util.Constant;
import src.yugui.entity.ReportDetail;
import src.yugui.entity.ValveHistoryInfo;
import src.yugui.entity.ValveReportInfo;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "报告历史信息表控制层", description = "ValveHistoryController")
@RestController
@RequestMapping("/report")
@Validated
public class ValveHistoryController extends BaseController {
    private static final Logger logger = Logger.getLogger(ValveHistoryController.class);

    @Resource(name = "valveHistory")
    private ValveHistoryService valveHistoryService;

    @Resource(name = "userNotifyService")
    private UserNotifyService userNotifyService;

    @Resource(name = "valveReport")
    private ValveReportService valveReportService;

    @Resource(name = "userService")
    private UserService userService;

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "根据编号查询安全阀校验报告表/记录表详情", response = ResponseMsg.class)
    @RequestMapping(value = "/getReportDetail", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getEnterpriceList(@RequestParam(value = "reportNo", required = true) String reportNo) {
        if (StringUtils.isEmpty(reportNo)) {
            return ResponseMsg.error("未提交报告编号");
        }
        ReportDetail detail = new ReportDetail();

        ValveReportInfo reportInfo = valveReportService.getValveReportByReportNo(reportNo);
        ValveHistoryInfo historyInfo = valveHistoryService.getValveHistoryInfoByReportNo(reportNo);
        detail.setReportInfo(reportInfo);
        detail.setHistoryInfo(historyInfo);

        return ResponseMsg.ok(detail);
    }

    @ApiOperation(value = "新建报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getNewReportList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getNewReportList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                        @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        UserInfo userInfo = getLoginUser();
        String userName = userInfo.getUserName();
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        Map<String, Object> valveHistoryMap = new HashMap<>();
        valveHistoryMap.put("createName", userName);
        logger.info("-------------->" + valveHistoryMap);
        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<ValveHistoryInfo> valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        //用PageInfo对结果进行包装
        PageInfo<ValveHistoryInfo> page = new PageInfo<ValveHistoryInfo>(valveHistoryInfos);
        return ResponseMsg.ok(page);
    }


    @ApiOperation(value = "审核报告 通过或不通过接口", response = ResponseMsg.class)
    @RequestMapping(value = "/checkResult", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg checkResult(@RequestBody(required = true) Map<String, String> infoMap) {
        UserInfo userInfo = getLoginUser();
        String tsStr = TimeTool.getCurrentTime();
        String reportNo = infoMap.get("reportNo");
        if (StringUtils.isEmpty(reportNo)) {
            return ResponseMsg.error("未提交报告编号reportNo");
        }
        String checkSignature = infoMap.get("checkSignature");
        if (StringUtils.isEmpty(checkSignature)) {
            return ResponseMsg.error("未提交审核人的电子签名checkSignature");
        }
        infoMap.put("modifyFlag", infoMap.get("flag"));
        infoMap.put("checkReason", infoMap.get("reason"));
        infoMap.put("checkName", userInfo.getUserName());
        infoMap.put("checkRealName", userInfo.getRealName());
        infoMap.put("checkTime", tsStr);
        logger.info("infoMap: ---->" + infoMap);

        Boolean upv = valveHistoryService.updateValvehistory(infoMap);
        Map<String, Object> valveNotifyMap = new HashMap<>();
        valveNotifyMap.put("preUser", userInfo.getUserName());
        valveNotifyMap.put("reportNo", reportNo);
        valveNotifyMap.put("realName", userInfo.getRealName());
        valveNotifyMap.put("flag", Integer.parseInt(infoMap.get("flag")));
        Boolean upu = userNotifyService.updateUserNotify(valveNotifyMap);
        if (!upu || !upv) {
            return ResponseMsg.error("提交审核失败！");
        }
        //报告表中添加审核人的电子签名
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("reportNo", reportNo);
        reportMap.put("checkSignature", checkSignature);
        valveReportService.updateValveReportInfo(reportMap);

        //个人信息修改成最新的电子签名
        if (StringUtils.isEmpty(userInfo.getSignature()) || !userInfo.getSignature().equals(checkSignature)) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("modifyTime", tsStr);//修改时间
            userMap.put("realName", userInfo.getRealName());//职位
            userMap.put("userName", userInfo.getUserName());//用户名
            userMap.put("signature", checkSignature);//电子签名
            userService.updateUser(userMap);
        }
        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("userName", userInfo.getUserName());
        String result;
        if (infoMap.get("flag").equals("2")) {
            result = "通过";

        } else {
            result = "不通过";
        }
        String message = "@{userName}审核了报告: @{reportNo} 审核结果：" + result;
        recordMap.put("message", message);
        recordMap.put("reportNo", reportNo);
        recordMap.put("operationTime", nowTime);
        recordMap.put("operationType", Constant.OPERATION_TYPE_NOTIFY);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok(userInfo.getUserName() + " 在 " + tsStr + " 审核了报告: " + reportNo);
    }

    @ApiOperation(value = "审核报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getCheckedReportList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getCheckedReportList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        UserInfo userInfo = getLoginUser();
        String userName = userInfo.getUserName();
        String realName = userInfo.getRealName();
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);

        List<ValveHistoryInfo> valveHistoryInfos = null;

        List<Integer> modiflyFlags = new ArrayList<>();
        modiflyFlags.add(Constant.REPORT_STATE_CHECK);
        modiflyFlags.add(Constant.REPORT_STATE_UNCHECK);
        Map<String, Object> valveHistoryMap = new HashMap<>();
        valveHistoryMap.put("modiflyFlags", modiflyFlags);
        if (realName.equals(Position.POSITION_SUPER_ADMIN)) {
            logger.info("-------------->" + valveHistoryMap);
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        if (realName.equals(Position.POSITION_CLERK)) {
            valveHistoryMap.put("createName", userName);
            logger.info("-------------->" + valveHistoryMap);
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        if (realName.equals(Position.POSITION_CHECK)) {
            valveHistoryMap.put("checkName", userName);
            logger.info("-------------->" + valveHistoryMap);
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        //用PageInfo对结果进行包装
        PageInfo<ValveHistoryInfo> page = new PageInfo<ValveHistoryInfo>(valveHistoryInfos);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "审批报告 通过或不通过接口", response = ResponseMsg.class)
    @RequestMapping(value = "/approveResult", method = RequestMethod.POST)
    @ResponseBody
    public ResponseMsg approveResult(@RequestBody(required = true) Map<String, String> infoMap) {
        String reportNo = infoMap.get("reportNo");
        String approveSignature = infoMap.get("approveSignature");
        if (StringUtils.isEmpty(approveSignature)) {
            return ResponseMsg.error("未提交审批人的电子签名approveSignature");
        }

        UserInfo userInfo = getLoginUser();
        String tsStr = TimeTool.getCurrentTime();
        infoMap.put("modifyFlag", infoMap.get("flag"));
        infoMap.put("approveName", userInfo.getUserName());
        infoMap.put("approveReason", infoMap.get("reason"));
        infoMap.put("approveRealName", userInfo.getRealName());
        infoMap.put("approveTime", tsStr);

        logger.info(infoMap);

        Boolean uph = valveHistoryService.updateValvehistory(infoMap);

        Map<String, Object> valveNotifyMap = new HashMap<>();
        valveNotifyMap.put("reportNo", reportNo);
        valveNotifyMap.put("preUser", userInfo.getUserName());
        valveNotifyMap.put("realName", userInfo.getRealName());
        valveNotifyMap.put("flag", Integer.parseInt(infoMap.get("flag")));
        Boolean upn = userNotifyService.updateUserNotify(valveNotifyMap);
        if (!upn || !uph) {
            return ResponseMsg.error("提交审批失败！");
        }
        //报告表中添加审批人的电子签名
        Map<String, Object> reportMap = new HashMap<>();
        reportMap.put("reportNo", reportNo);
        reportMap.put("approveSignature", approveSignature);
        valveReportService.updateValveReportInfo(reportMap);

        //个人信息修改成最新的电子签名
        if (StringUtils.isEmpty(userInfo.getSignature()) || !userInfo.getSignature().equals(approveSignature)) {
            Map<String, String> userMap = new HashMap<>();
            userMap.put("signature", approveSignature);//电子签名
            userMap.put("realName", userInfo.getRealName());//职位
            userMap.put("modifyTime", tsStr);//修改时间
            userMap.put("userName", userInfo.getUserName());//用户名
            userService.updateUser(userMap);
        }
        //向记录表添加数据
        String result;
        if (infoMap.get("flag").equals("4")) {
            result = "通过";

        } else {
            result = "不通过";
        }
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("userName", userInfo.getUserName());
        String message = "@{userName}审批了报告: @{reportNo}审批结果：" + result;
        recordMap.put("message", message);
        recordMap.put("operationTime", nowTime);
        recordMap.put("reportNo", reportNo);
        recordMap.put("operationType", Constant.OPERATION_TYPE_NOTIFY);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok("审批成功！");
    }


    @ApiOperation(value = "审批报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getApproveReportList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getApproveReportList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                            @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        UserInfo userInfo = getLoginUser();
        String userName = userInfo.getUserName();
        String realName = userInfo.getRealName();
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);

        List<Integer> modiflyFlags = new ArrayList<>();
        modiflyFlags.add(Constant.REPORT_STATE_APPROVE);
        modiflyFlags.add(Constant.REPORT_STATE_UNAPPROVE);
        Map<String, Object> valveHistoryMap = new HashMap<>();
        valveHistoryMap.put("modiflyFlags", modiflyFlags);

        List<ValveHistoryInfo> valveHistoryInfos = null;
        if (realName.equals(Position.POSITION_SUPER_ADMIN)) {
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        if (realName.equals(Position.POSITION_CHECK)) {
            valveHistoryMap.put("checkName", userName);
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        if (realName.equals(Position.POSITION_APPROVSL)) {
            valveHistoryMap.put("approveName", userName);
            valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        }
        //用PageInfo对结果进行包装
        PageInfo<ValveHistoryInfo> page = new PageInfo<ValveHistoryInfo>(valveHistoryInfos);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "归档报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getFileReportList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getFileReportList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                         @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);

        List<Integer> modiflyFlags = new ArrayList<>();
        modiflyFlags.add(Constant.REPORT_STATE_FILE);

        Map<String, Object> valveHistoryMap = new HashMap<>();
        valveHistoryMap.put("modiflyFlags", modiflyFlags);
        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<ValveHistoryInfo> valveHistoryInfos = valveHistoryService.getValveHistoryList(valveHistoryMap);
        //用PageInfo对结果进行包装
        PageInfo<ValveHistoryInfo> page = new PageInfo<ValveHistoryInfo>(valveHistoryInfos);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "归档接口", response = ResponseMsg.class)
    @RequestMapping(value = "/addFileReport", method = {RequestMethod.POST})
    public ResponseMsg addFileReport(@RequestBody(required = true) Map<String, String> infoMap) {
        String reportNo = infoMap.get("reportNo");
        UserInfo userInfo = getLoginUser();
        String realName = userInfo.getRealName();
        if (realName.equals(Position.POSITION_CLERK) || realName.equals(Position.POSITION_CHECK)) {
            return ResponseMsg.error("您没有归档权限！");
        }
        String tsStr = TimeTool.getCurrentTime();
        infoMap.put("modifyFlag", String.valueOf(Constant.REPORT_STATE_FILE));
        infoMap.put("fileName", userInfo.getUserName());
        infoMap.put("fileRealName", userInfo.getRealName());
        infoMap.put("fileTime", tsStr);

        Boolean uvh = valveHistoryService.updateValvehistory(infoMap);

        Map<String, Object> valveNotifyMap = new HashMap<>();
        valveNotifyMap.put("reportNo", infoMap.get("reportNo"));
        valveNotifyMap.put("flag", Constant.REPORT_STATE_FILE);
        valveNotifyMap.put("realName", userInfo.getRealName());
        valveNotifyMap.put("preUser", userInfo.getUserName());
        Boolean uun = userNotifyService.updateUserNotify(valveNotifyMap);
        if (!uvh || !uun) {
            return ResponseMsg.error("报告归档失败！");
        }

        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("operationTime", nowTime);
        recordMap.put("userName", userInfo.getUserName());
        String message = "@{userName}归档了报告: @{reportNo}查看报告详情";
        recordMap.put("message", message);
        recordMap.put("reportNo", reportNo);
        recordMap.put("operationType", Constant.OPERATION_TYPE_NOTIFY);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok("归档成功！");
    }
}
