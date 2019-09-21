package src.yugui.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import src.yugui.common.BaseMapper;
import src.yugui.entity.UserInfo;
import src.yugui.entity.UserNotify;

import java.util.List;
import java.util.Map;

/**
 * @Description: mapper接口
 * @Author: XiaoPanPan
 * @Date: 2019/8/6 11:12
 */
@Mapper
@Repository(value = "userNotifyMapper")
public interface UserNotifyMapper extends BaseMapper<UserInfo> {

    Boolean insertUsernotify(Map<String, String> valveNotifyMap);

    List<UserNotify> getValveNotifyList(Map<String, Object> valveNotifyMap);

    List<UserNotify> getUserNotifyByUserNameAndFlag(@Param("userName") String userName,@Param("flag") Integer flag);

    Boolean updateUsernotify(Map<String, Object> valveNotifyMap);

    List<String> getgetpreUser();

}
