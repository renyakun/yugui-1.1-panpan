package src.yugui.service;

import src.yugui.entity.UserNotify;

import java.util.List;
import java.util.Map;

/**
 * @Description: ValveHistoryService接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:19
 */

public interface UserNotifyService {
    /**
     * 分页查询UserNotify表信息
     */
    List<UserNotify> getUserNotifyList(Map<String, Object> valveNotifyMap);

    /**
     * 向UserNotify表插入1条数据
     */
    Boolean insertUserNotify(Map<String, String> valveNotifyMap);

    /**
     * 通过userName和flag查找
     */
    List<UserNotify> getUserNotifyByUserNameAndFlag(String userName,Integer flag);

    /**
     * 修改UserNotify表信息
     */
    Boolean updateUserNotify(Map<String, Object> valveNotifyMap);

    /**
     * 查询报告处理人
     */
    List<String> getgetpreUser();
}
