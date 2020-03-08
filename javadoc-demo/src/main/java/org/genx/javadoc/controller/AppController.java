package org.genx.javadoc.controller;

import org.genx.javadoc.common.Page;
import org.genx.javadoc.common.ResponseEntity;
import org.genx.javadoc.entity.AppConfigure;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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
@RequestMapping(value = {"/api/v1/app", "/app"})
public class AppController {

    /**
     * 获取APP首页数据
     * @param clientType 客户端类型
     * @param version 版本号
     * @param channel 渠道名称
     */
    @ResponseBody
    @RequestMapping(value = {"/home", "/homeIndex"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<AppConfigure>> homeInfo(Integer clientType, String version, String channel) {
        return null;
    }

    /**
     * 获取APP首页数据
     * 测试 类的内部 返回的是包装后的泛型 List<T>
     * @param clientType 客户端类型
     * @param version 版本号
     * @param channel 渠道名称
     */
    @ResponseBody
    @RequestMapping(value = "/homeInfo2", method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<Page<AppConfigure>> homeInfo2(Integer clientType, String version, String channel) {
        return null;
    }


    @ResponseBody
    @GetMapping(value = "/homeInfo3", produces = "application/json;charset=UTF-8")
    public ResponseEntity<Map<String, List<Page<AppConfigure>>>> homeInfo3(Integer clientType, String version, String channel) {
        return null;
    }

}
