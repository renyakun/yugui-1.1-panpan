package src.yugui.entity;

import lombok.Data;

import javax.persistence.Table;
import java.io.Serializable;

@Data
@Table(name = "valvehistory")
public class ValveHistoryInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String reportNo;
    private Integer modifyFlag;
    private String createName;
    private String createRealName;
    private String createTime;

    private String checkName;
    private String checkRealName;
    private String checkReason;
    private String checkTime;

    private String approveName;
    private String approveRealName;
    private String approveReason;
    private String approveTime;

    private String fileName;
    private String fileRealName;
    private String fileTime;

    //以下为安全阀校验记录表某些字段
    private String deviceType;//设备型号
    private String nominalDiameter;//公称通径
    private String channelDiameter;//流道直径
    private String pressureLevel;//压力级别范围
    private String madeCompany;//制造单位
    private String madeLicenseNo;//制造许可证编号
    private String factoryTime;//出厂日期
    private String gaugeAccuracy;//压力表精度
    private String checkMediumTemperature;//检验介质温度
    private String checkAppearance;//外观检查:合格或不合格
    private String firstSettingPressure;//第一次试验实际整定压力
    private String firstSealTestPressure;//第一次密封测试压力
    private String secondSettingPressure;//第二次试验实际整定压力
    private String secondSealTestPressure;//第二次密封测试压力
    private String thirdSettingPressure;//第三次试验实际整定压力
    private String thirdSealTestPressure;//第三次密封测试压力
    private String checkEffectiveTime;//校验有效日期

}
