package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValveHistory implements Serializable {
    private static final long serialVersionUID = 7899626061518643099L;

    private String checkReason;//审核不通过的原因
    private String approveReason;//审批不通过的原因

}
