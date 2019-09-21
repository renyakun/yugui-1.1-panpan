package src.yugui.entity;

import lombok.Data;

@Data
public class NotifyInfo {
    private int checkNum;         //我的待审核报告数量 1
    private int proveNum;         //我的待审批报告数量 3

    private int newReportNum;     //今日新建报告数量 0
    private int checkReportNum;   //今日已审核报告数量 2/12
    private int aprroveReportNum; //今日已审批报告数量 4/14
    private int fileReportNum;    //今日已归档报告数量 5

}
