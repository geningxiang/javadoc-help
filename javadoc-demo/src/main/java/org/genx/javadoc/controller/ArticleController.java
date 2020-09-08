package org.genx.javadoc.controller;

import io.swagger.annotations.ApiOperation;
import org.genx.javadoc.common.Page;
import org.genx.javadoc.common.ResponseEntity;
import org.genx.javadoc.common.ResponseListEntity;
import org.genx.javadoc.entity.Article;
import org.genx.javadoc.field.ArticleQueryField;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/7 22:52
 */
@RestController
@RequestMapping(value = "/api")
public class ArticleController {

    /**
     * 根据条件分页查询资讯
     * @param articleQueryField 查询条件
     * @return
     */
    @GetMapping(value = "/articles")
    public ResponseEntity<Page<Article>> queryByDate(@Valid ArticleQueryField articleQueryField) {
        return new ResponseEntity();
    }

    /**
     * 查询单条资讯
     * @param id 资讯ID {@link Article#getId()}
     * @return
     */
    @GetMapping(value = "/article/{id}")
    public ResponseEntity<Article> queryById(@PathVariable("id") @NotNull @Min(value = 1) Long id) {
        return new ResponseEntity();
    }


    @GetMapping(value = "/articles/{tag}")
    @ApiOperation(value = "根据tag查询资讯", notes = "")
    public ResponseListEntity<Page<Article>> queryByTag(@PathVariable("tag") String tag) {
        return new ResponseListEntity();
    }

    /**
     * 添加资讯
     * @param article 资讯对象
     * @return
     */
    @PostMapping(value = "/article")
    public ResponseEntity<Article> create(@RequestBody Article article) {
        return null;
    }

    /**
     * 修改资讯
     * @param id 资讯ID
     * @param article 资讯对象
     * @return
     */
    @PutMapping(value = "/article/{id}")
    public ResponseEntity<Article> update(@PathVariable("id") Long id, @RequestBody Article article) {
        return null;
    }


}
