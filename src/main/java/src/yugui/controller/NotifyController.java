package src.yugui.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import src.yugui.common.ResponseMsg;
import src.yugui.common.TimeTool;
import src.yugui.common.constant.Position;
import src.yugui.entity.NotifyInfo;
import src.yugui.entity.Record;
import src.yugui.entity.UserInfo;
import src.yugui.entity.ValveHistoryNotify;
import src.yugui.service.RecordService;
import src.yugui.service.ValveHistoryService;
import src.yugui.util.Constant;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: 处理消息通知
 * @Author: XiaoPanPan
 * @Date: 2019/9/11 16:55
 */
@RestController
@Api(tags = "消息通知控制层", description = "NotifyController")
@RequestMapping("/report")
public class NotifyController extends BaseController {
    private static final Logger logger = Logger.getLogger(ValveReportController.class);

    @Resource(name = "valveHistory")
    private ValveHistoryService valveHistoryService;

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "获取今日不同状态下各有多少份报告数量接口", response = ResponseMsg.class)
    @GetMapping("/getTodayNotify")
    public ResponseMsg getNotify() {
        String startTime = TimeTool.getTodayStartTime();//今日的开始时间
        String endTime = TimeTool.getTodayEndTime();//今日的结束时间
        logger.info(startTime + "-------------" + endTime);

        NotifyInfo notifyInfo = new NotifyInfo();//总通知消息封装
        List<Integer> modiflyFlag = new ArrayList<>();
        List<ValveHistoryNotify> valveHistoryNotifies;
        //今日新建报告数量
        modiflyFlag.add(Constant.REPORT_STATE_NEW);
        valveHistoryNotifies = valveHistoryService.getReportNumByModiflyFlagAndTime(modiflyFlag, startTime, endTime);
        int newReportNum = valveHistoryNotifies.size();
        notifyInfo.setNewReportNum(newReportNum);

        //今日已审核报告数量
        modiflyFlag = new ArrayList<>();
        modiflyFlag.add(Constant.REPORT_STATE_CHECK);
        modiflyFlag.add(Constant.REPORT_STATE_UNCHECK);
        valveHistoryNotifies = valveHistoryService.getReportNumByModiflyFlagAndTime(modiflyFlag, startTime, endTime);
        int checkNum = valveHistoryNotifies.size();
        notifyInfo.setCheckReportNum(checkNum);

        //今日已审批报告数量
        modiflyFlag = new ArrayList<>();
        modiflyFlag.add(Constant.REPORT_STATE_APPROVE);
        modiflyFlag.add(Constant.REPORT_STATE_UNAPPROVE);
        valveHistoryNotifies = valveHistoryService.getReportNumByModiflyFlagAndTime(modiflyFlag, startTime, endTime);
        int approveNum = valveHistoryNotifies.size();
        notifyInfo.setAprroveReportNum(approveNum);

        //今日归档报告数量
        modiflyFlag = new ArrayList<>();
        modiflyFlag.add(Constant.REPORT_STATE_FILE);
        valveHistoryNotifies = valveHistoryService.getReportNumByModiflyFlagAndTime(modiflyFlag, startTime, endTime);
        int fileNum = valveHistoryNotifies.size();
        notifyInfo.setFileReportNum(fileNum);
        return ResponseMsg.ok(notifyInfo);
    }

    @ApiOperation(value = "最新动态接口", response = ResponseMsg.class)
    @GetMapping("/getReportNotify")
    public ResponseMsg getReportNotify() {
        List<Record> records = recordService.getRecordList();
        return ResponseMsg.ok(records);
    }

    @ApiOperation(value = "通知或代办接口", response = ResponseMsg.class)
    @GetMapping("/getNotifyOrEvent")
    public ResponseMsg getNotifyOrEvent() {
        UserInfo userInfo = getLoginUser();
        String userName = userInfo.getUserName();

        List<Record> records = recordService.getRecordListOfNotify();
        List<Record> notifys = new ArrayList<>();

        for (Record record : records) {
            if (record.getUserName().equals(userName)) {
                if (Constant.OPERATION_TYPE_NOTIFY.equals(record.getOperationType())) {
                    notifys.add(record);
                }
            }
            if (!StringUtils.isEmpty(record.getPreUser()) && record.getPreUser().equals(userName)) {
                if (record.getOperationType().equals(Constant.OPERATION_TYPE_EVENT)) {
                    notifys.add(record);
                }
            }
        }
        return ResponseMsg.ok(notifys);
    }

    @ApiOperation(value = "删除通知或代办接口", response = ResponseMsg.class)
    @PostMapping("/deleteNotifyOrEvent")
    public ResponseMsg deleteNotifyOrEvent(@RequestBody(required = true) Map<String, Object> notifyMap) {
        Object ids = notifyMap.get("ids");
        logger.info(ids);
        if (ids == null || ids == "") {
            return ResponseMsg.error("未选择ids");
        }
        List<Long> noyifyIds = (List<Long>) ids;
        recordService.deleteNotifyOrEvent(noyifyIds);
        return ResponseMsg.ok("删除成功！");
    }
}