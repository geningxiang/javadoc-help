package com.genx.javadoc.entity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/1 23:27
 */
public class UserLoginField {

    /**
     * 用户名
     */
    @NotEmpty
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
    private String passWord;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

}
