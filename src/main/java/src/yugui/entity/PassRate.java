package src.yugui.entity;

import lombok.Data;

/**
 * @Description: 今日审核和审批的通过率实体
 * @Author: XiaoPanPan
 * @Date: 2019/9/25 9:42
 */
@Data
public class PassRate {
    private Double checkPassRate;
    private Double approvePassRate;

}
