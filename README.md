# javadoc-help

基于com.sun.javadoc解析源码，生成接口文档

当前版本1.1.0-SNAPSHOT，新增泛型解析
 

## 在线体验
以下是在gitee上找了几个开源项目生成的接口文档
- [JeecgBoot](http://47.100.63.232:8088/docs/jeecg-boot/index.html)
- [Guns](http://47.100.63.232:8088/docs/guns/index.html)
- [RuoYi](http://47.100.63.232:8088/docs/ruoyi/index.html)
- [zheng](http://47.100.63.232:8088/docs/zheng/index.html)


```
/**
     * 查询用户好友列表
     * @param userToken 用户令牌
     */
    @RequestMapping(value = "/friends", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
    public ResponseEntity<List<User>> friends(String userToken) {
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
    <version>1.1.0-SNAPSHOT</version>
</plugin>
```

```
mvn package javadoc-mvn:javaDoc
```

##接下来
- 梳理一遍新增代码
- 增加多模块项目的支持