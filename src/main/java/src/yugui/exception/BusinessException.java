package src.yugui.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import src.yugui.util.ApiCode;
import src.yugui.util.Response;

/**
 * 全局自定义异常
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {

    private ApiCode apiCode;
    private String message;

    public BusinessException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.apiCode = apiCode;
        this.message = apiCode.getMessage();
    }

    public BusinessException(ApiCode apiCode, String message) {
        super(message);
        this.apiCode = apiCode;
        this.message = message;
    }

    public BusinessException(int code, String message) {
        super(message);
        this.apiCode = ApiCode.getApiCode(code);
        this.message = message;
    }

    public BusinessException(Response response) {
        super(response.getMsg());
        this.apiCode = ApiCode.getApiCode(response.getCode());
        this.message = response.getMsg();
    }

}
