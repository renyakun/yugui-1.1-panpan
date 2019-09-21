package src.yugui.intercept;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import src.yugui.common.ResponseMsg;
import src.yugui.common.YgConstants;
import src.yugui.entity.UserInfo;
import src.yugui.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户操作登录拦截器
 * @Author: XiaoPanPan
 * @Date: 2019/8/22 10:57
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = Logger.getLogger(UserLoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        // 在请求处理之前进行调用（Controller方法调用之前）,返回true才会继续往下执行，返回false取消当前请求
        UserInfo loginUser = (UserInfo) httpServletRequest.getSession().getAttribute(YgConstants.LOGIN_USER);
        if (loginUser == null) {
            logger.info("用户未登录或登录已失效。。");
            WebUtils.writerJson(ResponseMsg.failure("登录已失效！请刷新页面重新登录！"), httpServletResponse);
//            httpServletResponse.sendRedirect("/report/user/logout");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
