package com.genx.javadoc.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/12 9:42
 */
@RestController
@RequestMapping(value={"/api", "/user"})
public class IndexController {


    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return
     * {
     *     "status": 200,
     *     "msg": "",
     *     "token": "5123312!sAd!sadh"      //令牌
     * }
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, String> login(String userName, String passWord) {
        return null;
    }
}