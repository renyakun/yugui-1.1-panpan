package src.yugui.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import src.yugui.common.ResponseMsg;
import src.yugui.common.TimeTool;
import src.yugui.common.YgConstants;
import src.yugui.service.UserService;
import src.yugui.entity.LoginRsp;
import src.yugui.entity.UserInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Api(tags = "用户登录登出管理层", description = "AccessController")
@RestController
@RequestMapping("/report/user")
@Validated
public class AccessController extends BaseController {
    private static final Logger logger = Logger.getLogger(AccessController.class);
    private static final String USER_NAME_PARAM = "user";
    private static final String USER_PASS_PARAM = "pwd";

    @Resource(name = "userService")
    private UserService userService;

    @ApiOperation(value = "登录接口", response = ResponseMsg.class)
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseMsg login(@RequestBody(required = false) Map<String, String> userMap) {
        logger.info("-------------------------userMap-------------------" + userMap);

        String userName = userMap.get(USER_NAME_PARAM);//用户名
        String pwd = userMap.get(USER_PASS_PARAM);//密码
        try {
            //当前用户已经登录
            UserInfo userInfo = getLoginUser();
            if (userInfo != null) {
                LoginRsp rsp = GetRsp(userInfo);
                return ResponseMsg.ok(rsp);
            }

            if (userMap.isEmpty()) {
                return ResponseMsg.error("没有登录凭据");
            }

            HttpSession oldSession = getRequest().getSession();
            oldSession.invalidate();//注销老的session

            HttpSession session = getRequest().getSession();
            UserInfo loginUser = userService.getUserByName(userName);

            if (null != loginUser) {
                if (loginUser.getEnable() != 1) {
                    return ResponseMsg.error("当前用户已被禁用");
                }
                if (!userName.equals(loginUser.getUserName())) {
                    return ResponseMsg.error("登录失败，请检查用户名是否正确!!");
                }
                if (!pwd.equals(loginUser.getUserPassword())) {
                    return ResponseMsg.error("密码输入有误！请重新输入！");
                }

                session.setAttribute(YgConstants.LOGIN_USER, loginUser);

                LoginRsp rsp = new LoginRsp();
                rsp.setUserName(loginUser.getUserName());
                rsp.setEnable(loginUser.getEnable());
                rsp.setUserLevel(loginUser.getUserLevel());
                logger.info("登录成功！----");
                //登录后修改登录时间
                String tsStr = TimeTool.getCurrentTime(); // 当前时间
                Map<String, String> map = new HashMap<>();
                map.put("loginTime", tsStr);
                map.put("userName", loginUser.getUserName());
                map.put("realName", loginUser.getRealName());
                logger.info("map ::------------------>>>.> " + map);
                Boolean upu = userService.updateUser(map);
                if (!upu) {
                    return ResponseMsg.error("登录发生了异常！");
                }
                return ResponseMsg.ok(rsp);
            } else {
                logger.info("登录失败");
                return ResponseMsg.error("登录失败，请检查用户名是否正确!");
            }

        } catch (Exception e) {
            logger.error(e);
            return ResponseMsg.error("登录异常");
        }
    }

    @ApiOperation(value = "登出接口", response = ResponseMsg.class)
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseMsg logout() {
        logger.info("退出成功！");
        try {
            HttpSession session = getRequest().getSession();
            UserInfo user = (UserInfo) session.getAttribute(YgConstants.LOGIN_USER);
            if (user == null) {
                return ResponseMsg.error("用户没有登录");
            }
            session.removeAttribute(YgConstants.LOGIN_USER);
            session.invalidate();
            return ResponseMsg.ok("退出登录");
        } catch (Exception e) {
            logger.error(e);
            return ResponseMsg.error("系统异常");
        }
    }

    @ApiOperation(value = "查看个人信息", response = ResponseMsg.class)
    @RequestMapping(value = "/getuser", method = RequestMethod.GET)
    public ResponseMsg getuser(@RequestBody(required = false) Map<String, String> userMap) {
        UserInfo userInfo = getLoginUser();
        if (userInfo != null) {
            return ResponseMsg.ok(userInfo);
        } else {
            return ResponseMsg.error("用户没有登录");
        }


    }

    LoginRsp GetRsp(UserInfo userInfo) {
        LoginRsp rsp = new LoginRsp();
        rsp.setUserName(userInfo.getUserName());
        rsp.setEnable(userInfo.getEnable());
        rsp.setUserLevel(userInfo.getUserLevel());
        return rsp;
    }

}

