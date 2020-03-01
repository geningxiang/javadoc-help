package com.genx.javadoc.controller;

import com.genx.javadoc.common.ResponseEntity;
import com.genx.javadoc.entity.User;
import com.genx.javadoc.entity.UserLoginField;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:42
 */
@RestController
@RequestMapping(value = "/api/user")
public class UserController {

    /**
     * 用户登录
     * @param userName 用户名
     * @param passWord 密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<User> login(
            @Validated
            @NotEmpty String userName,
            @Validated
            @NotBlank
            @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
                    String passWord) {

        ResponseEntity responseEntity = new ResponseEntity<User>();
        responseEntity.setCode(200);
        responseEntity.setData(new User());
        return responseEntity;
    }

    /**
     * 用户登录 - 对象接收参数
     * @param user
     * @return {
     *  "code": 200,
     *  "msg": "ok",
     *  "data": {
     *      "id": 1,                            //用户ID
     *      "userName": "张三",                  //用户名
     *      "token": "HINASDKBBH5123SH238",     //用户令牌
     *  }
     * }
     */
    @RequestMapping(value = "/loginByBean", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<User> loginByBean(@Validated UserLoginField user, BindingResult result, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("userName=" + user.getUserName());
        System.out.println("passWord=" + user.getPassWord());
        if (result.hasErrors()) {
            for (ObjectError allError : result.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }
        return new ResponseEntity();
    }


    /**
     * 登出
     * @param userToken 用户令牌
     * @return {
     *  "code": 200,
     *  "msg": "已退出登录",
     *  "data": "已退出登录"
     * }
     */
    @Deprecated
    @RequestMapping(value = "/logout", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<String> logout(String userToken) {
        return new ResponseEntity();
    }


    /**
     * 查询用户配置
     * 测试一下返回类型中包含数组的情况
     * @param userToken 用户令牌
     * @return
     */
    @RequestMapping(value = "/queryConfigList", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, String[]>> query(String userToken) {
        return new ResponseEntity();
    }

    /**
     * 查询用户好友列表
     * @param userToken 用户令牌
     * @return
     */
    @RequestMapping(value = "/friends", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<User>> friends(String userToken) {
        return null;
    }
}

