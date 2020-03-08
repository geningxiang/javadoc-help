package org.genx.javadoc.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import org.genx.javadoc.common.Page;
import org.genx.javadoc.common.ResponseEntity;
import org.genx.javadoc.field.ArticleQueryField;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/7 22:52
 */
@RestController
@RequestMapping(value = "/api/article")
public class ArticleController {

    @GetMapping(value = "/qeury")
    @ApiOperation(value = "查询资讯", notes = "")
    public ResponseEntity<Page<JSONObject>> qeuryByDate(@Valid ArticleQueryField articleQueryField) {
        return new ResponseEntity();
    }
}
