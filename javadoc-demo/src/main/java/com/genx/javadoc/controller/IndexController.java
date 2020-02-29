package com.genx.javadoc.controller;

import com.genx.javadoc.common.ResponseBody;
import com.genx.javadoc.entity.User;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

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

    private Logger logger = Logger.getLogger(IndexController.class.getName());

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
    public ResponseBody<User> login(
            @Validated
            @NotEmpty String userName,
            @Validated
            @NotBlank
            @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
                    String passWord) {
        System.out.println("userName=" + userName);
        System.out.println("passWord=" + passWord);

        this.logger.info("lalala");

        Logger.getLogger("aaa");

        ResponseBody responseBody = new ResponseBody<User>();
        responseBody.setCode(200);
        responseBody.setData(new User());
        return responseBody;
    }

    /**
     * 登录
     * @param user
     * @return {
     * "status": 200,
     * "msg": "ok",
     * "token": "HINASDKBBH5123SH238"      //令牌
     * }
     */
    @RequestMapping(value = "/loginByBean", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> loginByBean(@Validated User user, BindingResult result) {
        System.out.println("userName=" + user.getUserName());
        System.out.println("passWord=" + user.getPassWord());
        if(result.hasErrors()){
            for (ObjectError allError : result.getAllErrors()) {
                System.out.println(allError.getDefaultMessage());
            }
        }
        Map<String, String> map = new HashMap(8);
        map.put("status", "200");
        map.put("msg", "登录成功");
        return map;
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


    @RequestMapping(value = "/query", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseBody<List<User>> query(){
        return new ResponseBody();

    }

}

