package src.yugui.entity;

import lombok.Data;

@Data
public class LoginRsp {
    private String userName;
    private int    enable;
    private int    userLevel;
}
