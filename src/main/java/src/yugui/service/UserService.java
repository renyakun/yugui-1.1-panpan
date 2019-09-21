package src.yugui.service;

import src.yugui.entity.UserInfo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: ValveHistoryService接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:19
 */

public interface UserService {
    /**
     * 获取UserInfo
     */
    UserInfo getUserByName(String userName);

    /**
     * 获取所有UserInfo
     */
    List<UserInfo> getUserList();

    /**
     * 查询用户表的userName字段
     */
    List<String> getUserName();

    /**
     * 增加一个用户
     */
    Boolean addUser(Map<String, Object> userMap);

    /**
     * 修改一个用户
     */
    Boolean updateUser(Map<String, String> userMap);

    /**
     * 删除用户
     */
    Boolean deleteUser(List<String> userNames, String modifyTime);

}
