package src.yugui.common;

import lombok.Data;

import java.io.Serializable;


/**
 * 返回前端信息实体类
 * @author dengdairong 2016 下午4:29:31
 */
@Data
public class ResponseMsg<T> implements Serializable{
    private static final long serialVersionUID = -7932267115746602337L;
    /**
     * 0:OK
     * 1:业务异常
     * 2:系统异常
     */
    private int status;
    /**
     * true：正常
     * false:异常
     */
    private boolean isOk;
    /**
     * 返回数据
     */
    private T data;
    /**
     * 错误信息
     */
    private String errMsg;

    public ResponseMsg() {
        super();
    }
    public ResponseMsg(int statu, boolean isOk) {
        super();
        this.status = statu;
        this.isOk = isOk;
    }
    public ResponseMsg(int statu, boolean isOk, String errMsg,T data) {
        super();
        this.status = statu;
        this.isOk = isOk;
        this.errMsg = errMsg;
        this.data = data;
    }
    public ResponseMsg(int statu, boolean isOk, T data) {
        super();
        this.status = statu;
        this.isOk = isOk;
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public boolean isOk() {
        return isOk;
    }
    public void setOk(boolean isOk) {
        this.isOk = isOk;
    }
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    public String getErrMsg() {
        return errMsg;
    }
    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public static ResponseMsg<String> ok(){
        return new ResponseMsg<>(0, true);
    }
    public static <T> ResponseMsg<T> ok(T data){
        return new ResponseMsg<>(0, true, data);
    }
    public static ResponseMsg<String> ok(String msg){
        return new ResponseMsg<>(0, true, "", msg);
    }
    public static ResponseMsg<String> error(){
        return new ResponseMsg<>(1,false);
    }
    public static <T>ResponseMsg<T> error(String msg){
        return new ResponseMsg<>(1,false,msg,null);
    }
    public static <T>ResponseMsg<T> error(String msg,T data){
        return new ResponseMsg<>(1,false,msg,data);
    }
    public static ResponseMsg<String> sysError(){
        return new ResponseMsg<>(2,false);
    }
    public static <T>ResponseMsg<T> sysError(String msg){
        return new ResponseMsg<>(2,false,msg,null);
    }

    public static <T> ResponseMsg<T> failure(String errMsg) {
        return new ResponseMsg<>(1,false,errMsg,null);
    }
}
