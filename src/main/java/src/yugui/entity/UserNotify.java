package src.yugui.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "usernotify")
public class UserNotify implements Serializable {
    private static final long serialVersionUID = 7899626061518643095L;

    private String reportNo;
    private String userName;
    private String preUser;
    private String realName;
    private String flag;
    private String createTime;

    private ValveHistory valveHistory;

}
