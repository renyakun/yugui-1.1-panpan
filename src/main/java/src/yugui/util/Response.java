package src.yugui.util;

import lombok.Data;

import java.io.Serializable;
import java.util.Collections;

/**
 * 统一返回Response对象
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Data
public final class Response<T> implements Serializable {


    private int status;
    private T data;
    private String errMsg;
    private boolean ok;

    private int code;
    private String msg;
    private boolean success;

    private Response() {
    }

    public static <T> Response<T> success() {
        return success(null);
    }

    public static <T> Response<T> success(T result) {
        Response<T> response = new Response<T>();
        ApiCode code = ApiCode.OK;
        response.success = true;
        response.code = code.getCode();
        response.msg = code.getMessage();
        response.data = result;
        if (result == null) {
            response.data = (T) Collections.emptyMap();
        }
        return response;
    }

    public static <T> Response<T> success(T result, String msg) {
        Response<T> response = new Response<T>();
        ApiCode code = ApiCode.OK;
        response.success = true;
        response.code = code.getCode();
        response.msg = msg;
        response.data = result;
        if (result == null) {
            response.data = (T) Collections.emptyMap();
        }
        return response;
    }

    public static <T> Response<T> failure() {
        Response<T> response = new Response<T>();
        response.success = false;
        response.code = ApiCode.ERROR.getCode();
        response.msg = ApiCode.ERROR.getMessage();
        response.data = (T) Collections.emptyMap();
        return response;
    }

    public static <T> Response<T> failure(ApiCode code) {
        Response<T> response = new Response<T>();
        response.success = false;
        response.code = code.getCode();
        response.msg = code.getMessage();
        response.data = (T) Collections.emptyMap();
        return response;
    }

    public static <T> Response<T> failure(ApiCode code, String msg) {
        Response<T> response = new Response<T>();
        response.success = false;
        response.code = code.getCode();
        response.msg = msg;
        response.data = (T) Collections.emptyMap();
        return response;
    }

    public static <T> Response<T> failure(String errMsg) {
        Response<T> response = new Response<T>();
        response.status = 1;
        response.data = null;
        response.errMsg = errMsg;
        response.ok = false;
        return response;
    }

}
