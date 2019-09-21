package src.yugui.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.yugui.mapper.UserNotifyMapper;
import src.yugui.service.UserNotifyService;
import src.yugui.entity.UserNotify;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Description: 实现类
 * @Author: XiaoPanPan
 * @Date: 2019/8/10 14:18
 */
@Service(value = "userNotifyService")
public class UserNotifyServiceImpl implements UserNotifyService {

    @Resource(name = "userNotifyMapper")
    private UserNotifyMapper userNotifyMapper;

    @Override
    public List<UserNotify> getUserNotifyList(Map<String, Object> valveNotifyMap) {
        return userNotifyMapper.getValveNotifyList(valveNotifyMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean insertUserNotify(Map<String, String> valveNotifyMap) {
        return userNotifyMapper.insertUsernotify(valveNotifyMap);
    }

    @Override
    public List<UserNotify> getUserNotifyByUserNameAndFlag(String userName,Integer flag) {
        return userNotifyMapper.getUserNotifyByUserNameAndFlag(userName,flag);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUserNotify(Map<String, Object> valveNotifyMap) {
        return userNotifyMapper.updateUsernotify(valveNotifyMap);
    }

    @Override
    public List<String> getgetpreUser() {
        return userNotifyMapper.getgetpreUser();
    }
}
