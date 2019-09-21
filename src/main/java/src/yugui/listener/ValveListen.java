package src.yugui.listener;


import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;

/**
 * @Description:
 * @Author: XiaoPanPan
 * @Date: 2019/9/7 11:12
 */
public class ValveListen implements ServletRequestListener {

    @Override
    public void requestDestroyed(ServletRequestEvent servletRequestEvent) {


    }

    @Override
    public void requestInitialized(ServletRequestEvent servletRequestEvent) {
        System.out.println("---------------------------->请求创建");

    }
}
