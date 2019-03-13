package com.genx.javadoc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:42
 */
@RestController
@RequestMapping(value = "/api")
public class IndexController {


    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return {
     * "status": 200,
     * "msg": "ok",
     * "token": "HINASDKBBH5123SH238"      //令牌
     * }
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> login(
            @NotEmpty String userName,
            @NotBlank
            @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
                    String passWord) {
        return null;
    }


    /**
     * 登出
     *
     * @param userToken 用户令牌
     * @return {
     * "status": 200,
     * "msg": "ok"
     * }
     */
    @Deprecated
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> logout(String userToken) {
        return null;
    }

}

