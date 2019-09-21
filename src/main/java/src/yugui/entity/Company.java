package src.yugui.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @Description: 公司表 diao
 * @Author: XiaoPanPan
 * @Date: 2019/8/14 15:32
 */
@Data
@Entity
@Table(name = "company")
public class Company implements Serializable {
    private static final long serialVersionUID = 7899626061518643100L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 公司名称
     */
    private String companyUse;
    /**
     * 公司地址
     */
    private String companyAddress;
    /**
     * 公司联系人
     */
    private String companyContacts;
    /**
     * 电话号码
     */
    private String telephone;
    /**
     * 是否启用
     */
    private Integer enable;

    private String updateTime;

    private String createTime;


}
