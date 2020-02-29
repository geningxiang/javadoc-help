package com.genx.javadoc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * app 信息
 * @author: genx
 * @date: 2019/3/13 23:35
 */
@Controller
@RequestMapping(value = "/api")
public class AppController {

    /**
     * 获取APP首页数据
     *
     * @return {
     * "status": 200,
     * "msg": "ok"
     * }
     */
    @RequestMapping(value = "/homeInfo", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public Map<String, List<String>> homeInfo() {
        return null;
    }

}
