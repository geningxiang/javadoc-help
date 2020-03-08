package org.genx.javadoc.field;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
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
    @NotBlank(message = "用户名不能为空")
    @Length(min = 5, max = 10, message = "用户名长度不合法")
    private String userName;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空")
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
