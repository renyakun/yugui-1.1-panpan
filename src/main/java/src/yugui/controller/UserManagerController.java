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
import src.yugui.service.RecordService;
import src.yugui.service.UserNotifyService;
import src.yugui.service.UserService;
import src.yugui.service.ValveHistoryService;
import src.yugui.entity.NotifyInfo;
import src.yugui.entity.UserInfo;
import src.yugui.entity.UserNotify;
import src.yugui.util.Constant;


import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "任务管理控制层", description = "UserManagerController")
@RestController
@RequestMapping("/report")
@Validated
public class UserManagerController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserManagerController.class);

    @Resource(name = "userService")
    private UserService userService;

    @Resource(name = "userNotifyService")
    private UserNotifyService userNotifyService;

    @Resource(name = "valveHistory")
    private ValveHistoryService valveHistoryService;

    @Autowired
    private RecordService recordService;

    @ApiOperation(value = "提交审核", response = ResponseMsg.class)
    @RequestMapping(value = "/getCheckUserList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getCheckUserList() {
        List<UserInfo> checkUserList = new ArrayList<UserInfo>();
        List<UserInfo> userList = userService.getUserList();
        for (UserInfo userInfo : userList) {
            int level = userInfo.getUserLevel();
//            if (((level >> Constant.CHECK_RIGHT_SHIFT) & 0x01) == 1) {
            if (level == Constant.CHECK_APPROVE_SHIFT || level == Constant.CHECK_RIGHT_SHIFT) {
                checkUserList.add(userInfo);
            }
        }
        return ResponseMsg.ok(checkUserList);
    }

    @ApiOperation(value = "提交审批", response = ResponseMsg.class)
    @RequestMapping(value = "/getApproveUserList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getApproveUserList() {
        List<UserInfo> approveUserList = new ArrayList<UserInfo>();
        List<UserInfo> userList = userService.getUserList();
        for (UserInfo userInfo : userList) {
            int level = userInfo.getUserLevel();
//            if (((level >> Constant.APPROVE_RIGHT_SHIFT) & 0x01) == 1) {
            if (level == Constant.CHECK_APPROVE_SHIFT || level == Constant.APPROVE_RIGHT_SHIFT) {
                approveUserList.add(userInfo);
            }
        }
        return ResponseMsg.ok(approveUserList);
    }

    @ApiOperation(value = "提交给审核员去审核接口", response = ResponseMsg.class)
    @RequestMapping(value = "/addNotifyCheckUser", method = {RequestMethod.POST})
    public ResponseMsg addNotifyCheckUser(@RequestBody(required = true) Map<String, String> valveNotifyMap) {
        if (StringUtils.isEmpty(valveNotifyMap.get("userName"))) {
            return ResponseMsg.error("未选择审核人！");
        }
        String reportNo = valveNotifyMap.get("reportNo");
        UserInfo userInfo = getLoginUser();
        String tsStr = TimeTool.getCurrentTime();
        Map<String, String> newValveNotifyMap = new HashMap<>();
        newValveNotifyMap.put("preUser", userInfo.getUserName());
        newValveNotifyMap.put("reportNo", reportNo);
        newValveNotifyMap.put("userName", valveNotifyMap.get("userName"));
        newValveNotifyMap.put("realName", userInfo.getRealName());
        newValveNotifyMap.put("flag", "1");
        newValveNotifyMap.put("createTime", tsStr);

        Boolean addUserNotify = userNotifyService.insertUserNotify(newValveNotifyMap);
        Boolean updateValvehistory = valveHistoryService.updateValvehistory(reportNo, Constant.REPORT_STATE_COMMIT_CHECK);
        if (!addUserNotify && !updateValvehistory) {
            return ResponseMsg.error("提交失败！");
        }

        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("operationTime", nowTime);
        recordMap.put("userName", userInfo.getUserName());
        String message = "@{userName}向@{preUser}提交了审核报告：@{reportNo}";
        recordMap.put("message", message);
        recordMap.put("reportNo", reportNo);
        recordMap.put("preUser", valveNotifyMap.get("userName"));
        recordMap.put("operationType", Constant.OPERATION_TYPE_EVENT);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok("提交成功！");
    }

    @ApiOperation(value = "任务处理接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getUserNotifyInfo", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getUserNotifyInfo() {
        NotifyInfo notifyInfo = new NotifyInfo();
        UserInfo userInfo = getLoginUser();

        List<UserNotify> checkList = userNotifyService.getUserNotifyByUserNameAndFlag(userInfo.getUserName(), Constant.REPORT_STATE_COMMIT_CHECK);
        List<UserNotify> approveList = userNotifyService.getUserNotifyByUserNameAndFlag(userInfo.getUserName(), Constant.REPORT_STATE_COMMIT_APPROVE);
        notifyInfo.setCheckNum(checkList.size());//设置待审核报告数量
        notifyInfo.setProveNum(approveList.size());//设置待审批报告数量
        return ResponseMsg.ok(notifyInfo);
    }

    @ApiOperation(value = "待审核报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getWaitCheckList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getWaitCheckList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                        @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {

        UserInfo userInfo = getLoginUser();
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);
        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<UserNotify> list = userNotifyService.getUserNotifyByUserNameAndFlag(userInfo.getUserName(), Constant.REPORT_STATE_COMMIT_CHECK);
        //用PageInfo对结果进行包装
        PageInfo<UserNotify> page = new PageInfo<UserNotify>(list);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "提交给审批员去审批接口", response = ResponseMsg.class)
    @RequestMapping(value = "/addNotifyApproveUser", method = {RequestMethod.POST})
    public ResponseMsg addNotifyApproveUser(@RequestBody(required = true) Map<String, String> infoMap) {
        if (StringUtils.isEmpty(infoMap.get("userName"))) {
            return ResponseMsg.error("未选择审批人！");
        }
        String reportNo = infoMap.get("reportNo");
        UserInfo userInfo = getLoginUser();
        String realName = userInfo.getRealName();
        if (realName.equals(Position.POSITION_CLERK)) {
            return ResponseMsg.error("您没有提交审批权限!");
        }

        String tsStr = TimeTool.getCurrentTime();
        Map<String, String> newUserNotifyMap = new HashMap<>();
        newUserNotifyMap.put("reportNo", reportNo);
        newUserNotifyMap.put("userName", infoMap.get("userName"));
        newUserNotifyMap.put("preUser", userInfo.getUserName());
        newUserNotifyMap.put("realName", userInfo.getRealName());
        newUserNotifyMap.put("flag", "3");
        newUserNotifyMap.put("createTime", tsStr);
        logger.info("newUserNotifyMap ----" + newUserNotifyMap);
        userNotifyService.insertUserNotify(newUserNotifyMap);
        valveHistoryService.updateValvehistory(reportNo, Constant.REPORT_STATE_COMMIT_APPROVE);

        //向记录表添加数据
        String nowTime = TimeTool.getNowTime();
        Map<String, Object> recordMap = new HashMap<>();
        recordMap.put("userName", userInfo.getUserName());
        recordMap.put("reportNo", reportNo);
        recordMap.put("operationTime", nowTime);
        String message = "@{userName}向@{preUser}提交了审批报告：@{reportNo}";
        recordMap.put("message", message);
        recordMap.put("preUser", infoMap.get("userName"));
        recordMap.put("operationType", Constant.OPERATION_TYPE_EVENT);
        recordService.addRecord(recordMap);
        return ResponseMsg.ok("提交成功!");
    }

    @ApiOperation(value = "待审批报告列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getWaitApproveList", method = RequestMethod.GET)
    @ResponseBody
    public ResponseMsg getWaitApproveList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                          @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        UserInfo userInfo = getLoginUser();
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);

        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<UserNotify> list = userNotifyService.getUserNotifyByUserNameAndFlag(userInfo.getUserName(), Constant.REPORT_STATE_COMMIT_APPROVE);
        //用PageInfo对结果进行包装
        PageInfo<UserNotify> page = new PageInfo<UserNotify>(list);
        return ResponseMsg.ok(page);
    }
}