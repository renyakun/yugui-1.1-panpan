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
import src.yugui.common.TimeTool;
import src.yugui.common.constant.Position;
import src.yugui.service.UserService;
import src.yugui.entity.UserInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Api(tags = "人员管理层", description = "AccessController")
@RestController
@RequestMapping("/report/user")
@Validated
public class UserController extends BaseController {
    private static final Logger logger = Logger.getLogger(UserController.class);

    @Resource(name = "userService")
    private UserService userService;

    @ApiOperation(value = "添加管理员/创建管理员接口", response = ResponseMsg.class)
    @RequestMapping(value = "/addUser", method = {RequestMethod.POST})
    public ResponseMsg addUser(@RequestBody(required = false) Map<String, Object> userMap) {
        UserInfo userInfo = getLoginUser();
        if (!userInfo.getRealName().equals("超级管理员")) {
            return ResponseMsg.error("您没有操作权限！请联系超级管理员！");
        }
        String userName = (String) userMap.get("userName");//用户名
        Integer realName = Integer.parseInt(userMap.get("realName").toString());//职位
        //检验用户名是否已经存在
        UserInfo userInfo2 = userService.getUserByName(userName);
        if (userInfo2 != null) {
            return ResponseMsg.error("用户名已存在！请重新输入！");
        }
        //不存在才执行
        String tsStr = TimeTool.getCurrentTime();//当前时间
        userMap.put("realName", Position.getType(realName));//职位转换
        userMap.put("createTime", tsStr);//创建时间
        userMap.put("enable", 1);//启用
        userMap.put("userLevel", realName);//等级审核员
        logger.info("userMap : ---------->>>>>>" + userMap);

        Boolean addUser = userService.addUser(userMap);
        if (!addUser) {
            return ResponseMsg.error("创建失败！");
        }
        return ResponseMsg.ok("创建成功！");
    }

    @ApiOperation(value = "安全设置/修改管理员信息接口", response = ResponseMsg.class)
    @RequestMapping(value = "/updateUser", method = {RequestMethod.POST})
    public ResponseMsg updateUser(@RequestBody(required = false) Map<String, String> userMap) {
        UserInfo userInfo = getLoginUser();
        //不存在才执行*/
        String modifyTime = TimeTool.getCurrentTime(); // 当前时间
        userMap.put("modifyTime", modifyTime);//修改时间
        userMap.put("userName", userInfo.getUserName());//用户名
        userMap.put("realName", userInfo.getRealName());//职位

        Boolean updateUser = userService.updateUser(userMap);
        if (!updateUser) {
            return ResponseMsg.error("修改失败！");
        }
        return ResponseMsg.ok("修改成功 ！");
    }

    @ApiOperation(value = "用户列表接口", response = ResponseMsg.class)
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public ResponseMsg listUser(@RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                                @RequestParam(defaultValue = "10", value = "pageSize") Integer pageSize) {
        UserInfo userInfo = getLoginUser();
        if (!userInfo.getRealName().equals("超级管理员")) {
            return ResponseMsg.error("您没有查看权限！");
        }
        //获取第1页，10条内容，默认查询总数count
        PageHelper.startPage(pageNum, pageSize);

        //封装了总数，封装了分页信息，封装了查询出来的数据
        List<UserInfo> list = userService.getUserList();
        //用PageInfo对结果进行包装
        PageInfo<UserInfo> page = new PageInfo<UserInfo>(list);
        return ResponseMsg.ok(page);
    }

    @ApiOperation(value = "删除用户/可批量删除", response = ResponseMsg.class)
    @RequestMapping(value = "/deleteUser", method = {RequestMethod.POST})
    public ResponseMsg deleteUser(@RequestBody(required = true) Map<String, Object> userMap) {
        Object users = userMap.get("users");
        logger.info(users);
        if (StringUtils.isEmpty(users.toString())) {
            return ResponseMsg.error("未选择用户 " + users);
        }
        List<String> userNames = (List<String>) users;
        String time = TimeTool.getCurrentTime();//当前时间
        Boolean delete = userService.deleteUser(userNames, time);
        return ResponseMsg.ok("删除用户成功！" + delete);
    }

    @ApiOperation(value = "查看个人的电子签名接口", response = ResponseMsg.class)
    @RequestMapping(value = "/getSignature", method = {RequestMethod.GET})
    public ResponseMsg getSignature() {
        UserInfo userInfo = getLoginUser();
        String userName = userInfo.getUserName();
        String signature = userService.getUserSignature(userName);
        return ResponseMsg.ok(signature);
    }


}

