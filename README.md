# javadoc-help
用过2种接口文档生成工具,感觉都不够完美
- Swagger 依赖于注解,侵入性太高
- ApiDoc 依赖于注释,通过node解析源代码,但信息不够全面,注释不符合java规范  

所以想自己写一个插件,根据标准的注释即可生成api文档、接口文档  
api文档 暂时只考虑 SpringMVC、SpringBoot


```
    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return 
     * 这里没办法 api文档需要自己填一下
     * 如果是接口文档, 有明确的返回类, 就可以根据返回类显示, 不需要再写这个return注释
     * {
     *   "status": 200,
     *   "msg": "ok",
     *   "token": "HINASDKBBH5123SH238"      //令牌
     * }
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public String login(
            @NotBlank String userName,
            @NotBlank
            @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
                    String passWord) {
        return null;
    }
```

已实现:  
  通过maven插件的形式, 自动扫描项目代码, 将类文档以json格式存储到 target/doc/javadoc.json  
  
可以在 javadoc-demo 模块 执行以下语句, 当然请先install一下 javadoc-core和javadoc-mvn-plugin
```
mvn package javadoc-mvn:javaDoc
```

下一步需要开始完善 前端展现 和 doc文档生成