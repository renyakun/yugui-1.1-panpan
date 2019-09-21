package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValveNoInfo implements Serializable {
    private static final long serialVersionUID = 7899626061518643096L;
    private int id;
    private String valveNo;
    private String valveState;
    private String createTime;
}
