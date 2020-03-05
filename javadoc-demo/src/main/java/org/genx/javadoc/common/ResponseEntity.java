package org.genx.javadoc.common;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/15 15:47
 */
public class ResponseEntity<T> {

    /**
     * 接口状态码
     */
    private int code;

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 具体的响应数据
     */
    private T data;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
