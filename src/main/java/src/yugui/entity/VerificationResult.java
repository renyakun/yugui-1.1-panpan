package src.yugui.entity;

import lombok.Data;

/**
 * @Description: 校验结论是否合格及其原因
 * @Author: XiaoPanPan
 * @Date: 2019/9/11 15:05
 */
@Data
public class VerificationResult {
    private String isQualified;//结论是否合格
    private String reason;//原因
}
