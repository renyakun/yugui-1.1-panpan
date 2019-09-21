package src.yugui.util;

import lombok.extern.slf4j.Slf4j;

/**
 * API返回状态码
 *
 *  @Author: XiaoPanPan
 *  @Date: 2019/8/22 10:57
 */
@Slf4j
public enum ApiCode {

    OK(0, "成功"),
    ERROR(-1, "系统繁忙"),
    UNAUTHORIZED(401, "未登录"),
    FORBIDDEN(403, "访问没有权限"),
    NOT_FOUND(404, "请求的URL地址不存在"),

    CONNECT_ERROR(50001, "服务连接异常"),
    CONNECT_TIMEOUT(50002, "连接超时"),

    MISSING_PARAMETER(40001, "缺少参数"),
    PARAMETER_TYPE_ERROR(40003, "参数类型错误"),
    INVALID_PARAMETERS(40002, "无效的参数"),
    NOT_READABLE(40003, "参数解析异常"),
    XML_FORMAT_ERROR(40004, "参数解析异常"),
    ILLEGAL_REQUEST(40005, "非法的请求"),

    MISSING_ACCESS_TOKEN(41000, "缺少accessToken参数"),
    INVALID_ACCESS_TOKEN(41001, "无效的accessToken参数"),
    INVALID_REFRESH_TOKEN(41002, "无效的refreshToken参数"),
    ACCESS_TOKEN_EXPIRED(41003, "accessToken已过期"),
    ACCESS_USER_LOGIN(41004, "用户未登录或登录已失效。。"),

    CERTIFICATE_UNKNOWN(42000, "证书无效"),
    READ_CERTIFICATE_ERROR(42001, "读取证书失败"),
    MISSING_SIGN_PARAMETERS(42002, "缺少proxySign签名参数"),
    SIGN_FAIL(42003, "无效签名"),

    APPID_FAIL(43000, "无效的appId"),
    MISSING_APPID_PARAMETERS(43001, "缺少appId参数"),
    UPLOAD_FILE_NOT_EXIST(43002, "文件路径不存在"),
    FILE_EMPTY(43003, "文件大小为0"),
    FTP_NOT_CONFIGURED(43004, "ftp未配置"),
    FTP_CONNECT_FAIL(43005, "ftp连接失败"),
    FTP_CREATE_DIRECTORY(43006, "ftp创建目录失败"),
    FILE_UPLOADING_IS_DIRECTORY(43007, "不能上传文件目录"),
    FILE_UPLOADING(43008, "该文件正在上传中"),
    FILE_UPLOADING_FAIL(43009, "文件上传失败"),
    FILE_DOWNLOAD_IS_DIRECTORY(43010, "不能下载文件目录"),
    FILE_DOWNLOADING(43011, "该文件正在下载中"),
    FILE_DOWNLOAD_FAIL(43012, "文件下载失败"),
    DOWNLOAD_FILE_NOT_EXIST(43013, "文件路径不存在"),
    FILE_ENCRYPT_FAIL(43014, "文件加密失败"),
    FILE_DECRYPT_FAIL(43015, "文件解密失败"),
    FILE_KEY_NOT_EXIST(43016, "无效的fileKey参数");

    private final int code;
    private final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ApiCode getApiCode(int code) {
        ApiCode[] values = ApiCode.values();
        for (ApiCode apiCode : values) {
            if (apiCode.code == code) {
                return apiCode;
            }
        }
        log.warn("code={}对应的ApiCode未找到", code);
        return ApiCode.INVALID_PARAMETERS;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}