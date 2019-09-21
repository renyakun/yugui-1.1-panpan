package src.yugui.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import src.yugui.mapper.UserMapper;
import src.yugui.service.UserService;
import src.yugui.entity.UserInfo;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @Description: 实现类
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:20
 */
@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Resource(name = "userMapper")
    private UserMapper userMapper;

    @Override
    public UserInfo getUserByName(String userName) {
        return userMapper.getUserByName(userName);
    }

    @Override
    public List<UserInfo> getUserList() {
        return userMapper.getUserList();
    }

    @Override
    public List<String> getUserName() {
        return userMapper.getUserName();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean addUser(Map<String, Object> userMap) {
        return userMapper.addUser(userMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updateUser(Map<String, String> userMap) {
        return userMapper.updateUser(userMap);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteUser(List<String> userNames, String modifyTime) {
        return userMapper.deleteUser(userNames, modifyTime);
    }

}


