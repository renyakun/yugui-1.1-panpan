package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 7899626061518643094L;

    private String userName;     //用户名
    private String userPassword; //密码
    private String realName;     //职位
    private String phone;        //电话
    private String email;        //邮箱
    private String signatureUrl;    //电子签名地址

    private Integer enable;
    private String modifyTime;
    private String createTime;
    private String loginTime;
    private Integer userLevel;
}


