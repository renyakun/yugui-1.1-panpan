package src.yugui.util;

public class Constant {
    public static final int REPORT_STATE_NEW = 0; //录入报告
    public static final int REPORT_STATE_COMMIT_CHECK = 1; //提交审核
    public static final int REPORT_STATE_CHECK = 2;  //审核通过
    public static final int REPORT_STATE_UNCHECK = 12;  //审核不通过

    public static final int REPORT_STATE_COMMIT_APPROVE = 3; // 提交审批
    public static final int REPORT_STATE_APPROVE = 4;  //审批通过
    public static final int REPORT_STATE_FILE = 5;  //报告归档

    public static final int REPORT_STATE_UNAPPROVE = 14;  //审批不通过

    public static final int CHECK_APPROVE_SHIFT = 0;
    public static final int CHECK_RIGHT_SHIFT = 1;
    public static final int APPROVE_RIGHT_SHIFT = 2;


    public static final String OPERATION_TYPE_NOTIFY = "notification";
    public static final String OPERATION_TYPE_EVENT = "event";
}

