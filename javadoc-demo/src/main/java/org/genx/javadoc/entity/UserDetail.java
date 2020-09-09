package org.genx.javadoc.entity;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/9/4 21:42
 */
public class UserDetail extends User {

    /**
     * 手机号码
     */
    private String mobile;

    /**
     * 设备串号
     */
    private String imei;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
