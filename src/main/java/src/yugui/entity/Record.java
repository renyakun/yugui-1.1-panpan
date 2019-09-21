package src.yugui.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 记录表实体类
 * @Author: XiaoPanPan
 * @Date: 2019/9/17 11:09
 */
@Data
public class Record implements Serializable {
    private static final long serialVersionUID = 7899626061518643095L;

    private Integer id;//主键ID
    private String userName;//操作人
    private String message;//操作信息
    private String reportNo;//报告编号
    private String preUser;//处理人
    private String operationTime;//操作时间
    private String operationType;//操作类型

}
