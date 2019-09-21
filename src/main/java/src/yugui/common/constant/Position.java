package src.yugui.common.constant;

import java.util.Map;

/**
 * @Description: 创建用户 职位数字转换
 * @Author: XiaoPanPan
 * @Date: 2019/8/8 10:21
 */
public class Position {
    public static final String POSITION_CHECK = "审核员";
    public static final String POSITION_APPROVSL = "审批员";
    public static final String POSITION_CLERK = "文员";
    public static final String POSITION_SUPER_ADMIN = "超级管理员";

    //将参数workPressure转成所对应的值
    public static String getType(Integer realName) {
        if (realName == 1) {
            return POSITION_CHECK;
        }
        if (realName == 2) {
            return POSITION_APPROVSL;
        }
        if (realName == 3) {
            return POSITION_CLERK;
        }
        return POSITION_SUPER_ADMIN;
    }

}
