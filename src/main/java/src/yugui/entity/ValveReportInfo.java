package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class ValveReportInfo implements Serializable {
    private static final long serialVersionUID = 7899626061518643097L;

    //生成报告用以下字段
    private String reportNo;//报告编号
    private String companyUse;//使用单位
    private String companyAddress;//单位地址
    private String companyContacts;//联系人
    private String telephone;//联系电话
    private String deviceNo;//设备代码、出厂编号
    private String installLocation;//安装位置
    private String valveType;//安全阀类型
    private String valveModel;//安全阀型号
    private String workPressure;//工作压力
    private String workMedium;//工作介质
    private String requireSettingPressure;//要求整定压力
    private String standard;//执行标准
    private String settingPressure;//整定压力
    private String sealTestPressure;//密封试验测试压力
    private String checkMode;//检验方式:离线校验/现场离线/现场检验
    private String checkMedium;//校验介质
    private String checkResult;//校验结果
    private String remarks;//备注
    private String checkExplain;//维护检修情况说明
    private String checkSignatureUrl;//审核人的电子签名 地址
    private String approveSignatureUrl;//审批人的电子签名 地址

}
