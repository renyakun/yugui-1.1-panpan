package src.yugui.controller;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import src.yugui.common.ResponseMsg;
import src.yugui.entity.UserNotify;
import src.yugui.service.UserNotifyService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(tags = "查询管理控制层", description = "UserNotifyController")
@RestController
@RequestMapping("/report")
@Validated
public class UserNotifyController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserNotifyController.class);

    @Resource(name = "userNotifyService")
    private UserNotifyService userNotifyService;

    @ApiOperation(value = "分页查询接口，报告编号模糊查询", response = ResponseMsg.class)
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseMsg getUserNotifyList(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                         @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize,
                                         @RequestParam(defaultValue = "", value = "reportNo") String reportNo,
                                         @RequestParam(defaultValue = "", value = "userName") String userName,
                                         HttpServletRequest request) {

        String flags = request.getParameter("flag");
        logger.info("flags:  --" + flags);

        Map<String, Object> valveNotifyMap = new HashMap<>();
        valveNotifyMap.put("reportNo", reportNo);
        valveNotifyMap.put("userName", userName);
        valveNotifyMap.put("flag", flags);
        if (!StringUtils.isEmpty(flags) && flags.contains(",")) {
            valveNotifyMap.remove("flag");//移除原来的flag
            String[] flagsArrs = flags.split(",");
            List<Integer> flagsArrl = new ArrayList<>();
            for (String flagsArr : flagsArrs) {
                flagsArrl.add(Integer.parseInt(flagsArr));
            }
            logger.info("flagsArrl：" + flagsArrl);
            valveNotifyMap.put("flagsArrl", flagsArrl);
        }
        logger.info("valveNotifyMap: --" + valveNotifyMap);

        //设置分页信息，分别是当前页数和每页显示的总记录数
        PageHelper.startPage(pageNum, pageSize);
        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<UserNotify> userNotifys = userNotifyService.getUserNotifyList(valveNotifyMap);

        //用PageInfo对结果进行包装
        PageInfo<UserNotify> page = new PageInfo<UserNotify>(userNotifys);

        logger.info("page    ------------------------------>" + page);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "报告处理人接口", response = ResponseMsg.class)
    @RequestMapping(value = "/preUser", method = {RequestMethod.GET})
    @ResponseBody
    public ResponseMsg getpreUser() {
        List<String> preUsers = userNotifyService.getgetpreUser();
        return ResponseMsg.ok(preUsers);
    }
}

