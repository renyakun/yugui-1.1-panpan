package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValveHistoryNotify implements Serializable {
    private static final long serialVersionUID = 7899626061518643090L;

    private String reportNo;
    private String createName;
    private String createTime;

    private String checkName;
    private String checkReason;
    private String checkTime;

    private String approveName;
    private String approveReason;
    private String approveTime;

    private String fileName;
    private String fileTime;

}
