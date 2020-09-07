package org.genx.javadoc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.genx.javadoc.common.Page;
import org.genx.javadoc.common.ResponseEntity;
import org.genx.javadoc.entity.AppConfigure;
import org.genx.javadoc.field.ArticleQueryField;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    @RequestMapping(name = "获取APP首页数据", value = {"/home", "/homeIndex"}, method = {RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<AppConfigure>> homeInfo(@RequestHeader(value = "User-Agent", defaultValue = "") String userAgent, @RequestParam("clientType") Integer clientType,  @RequestBody String version, String channel) {
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

    @ResponseBody
    @GetMapping(value = "/test", produces = "application/json;charset=UTF-8")
    public String test(@RequestParam(name = "field", required = false) ArticleQueryField articleQueryField, @RequestBody JSONObject data) {
        System.out.println("articleQueryField: " + JSON.toJSONString(articleQueryField));
        return JSON.toJSONString(data);
    }

    @ResponseBody
    @GetMapping(value = "/testList", produces = "application/json;charset=UTF-8")
    public String testList(@RequestParam(name = "field", required = false) ArticleQueryField articleQueryField, @RequestBody JSONObject data) {
        System.out.println("articleQueryField: " + JSON.toJSONString(articleQueryField));
        return JSON.toJSONString(data);
    }

}
