# javadoc-help

[接口文档界面展示](https://geningxiang.github.io/javadoc-help/javadoc-ui/)


无需任何依赖，只需要规范注释，就可以通过maven插件生成接口文档


```
    /**
     * 登录
     * @param userName 用户名
     * @param passWord 密码
     * @return 
     * 这里没办法 只能自己填一下类json格式
     * 如果有明确的返回类, 就可以根据具体的类来读取返回格式, 不需要再写这个return注释
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


暂时只上传了私有仓库,如果要体验只能自己加一下 pluginRepository
```
    <!-- 插件仓库地址 -->
    <pluginRepositories>
        <!-- 默认先请求阿里云 -->
        <pluginRepository>
            <id>aliyun</id>
            <url>https://maven.aliyun.com/nexus/content/groups/public/</url>
            <layout>default</layout>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </pluginRepository>
        <!-- 我的私有仓库地址 -->
        <pluginRepository>
            <id>caimao</id>
            <url>http://60.190.13.162:6118/maven/</url>
            <snapshots>
                <enabled>true</enabled>
            </snapshots>
            <releases>
                <enabled>true</enabled>
            </releases>
        </pluginRepository>
    </pluginRepositories>
```
```
<plugin>
    <groupId>com.genx.javadoc</groupId>
    <artifactId>javadoc-mvn-plugin</artifactId>
    <version>1.0.1</version>
</plugin>
```

```
mvn package javadoc-mvn:javaDoc
```

暂时只写了SpringMVC的Controller

后续考虑  
接入到Automate2  
记录接口的每一次变化  
实现后端接口变化的实时推送  
为开发人员提供完整的项目的接口变动记录及各版本之间的差异
