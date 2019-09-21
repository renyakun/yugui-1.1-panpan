package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import src.yugui.common.BaseMapper;
import src.yugui.entity.UserInfo;

import java.util.List;
import java.util.Map;

/**
 * @Description: mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Repository(value = "userMapper")
public interface UserMapper extends BaseMapper<UserInfo> {

    UserInfo getUserByName(@Param("userName") String userName);

    List<UserInfo> getUserList();

    List<String> getUserName();

    Boolean addUser(Map<String, Object> userMap);

    Boolean updateUser(Map<String, String> userMap);

    Boolean deleteUser(@Param("userNames") List<String> userNames, @Param("modifyTime") String modifyTime);

}
