# javadoc-help

基于com.sun.javadoc解析源码，生成接口文档

### 在线体验
以下是在gitee上找了几个开源项目生成的接口文档
- [JeecgBoot](http://47.100.63.232:8088/docs/jeecg-boot/index.html)
- [Guns](http://47.100.63.232:8088/docs/guns/index.html)
- [RuoYi](http://47.100.63.232:8088/docs/ruoyi/index.html)
- [zheng](http://47.100.63.232:8088/docs/zheng/index.html)

### 版本说明

| 版本号 | 说明 |
| :----: | ------ |
| 1.0.0 | 完成基础解析功能 | 
| 1.1.0 | 添加泛型解析，新增解析结果的实体类(后续将要持久化) |
| 1.1.1 | 定义插件，解析各种注释(lombok、jsr-305) | 


### MAVEN
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
    <groupId>org.genx.javadoc</groupId>
    <artifactId>javadoc-mvn-plugin</artifactId>
    <version>1.1.1</version>
</plugin>
```

```
mvn javadoc-mvn:javaDoc
```

###常见问题
```
使用 Mybatis-Plus的童鞋
mybatis-plus-extension:3.1.2
com.baomidou.mybatisplus.extension.plugins.pagination.Page
17行 import org.jetbrains.annotations.Nullable;
导致解析是 “找不到org.jetbrains.annotations.Nullable的类文件”

请添加
<dependency>
    <groupId>org.jetbrains</groupId>
    <artifactId>annotations</artifactId>
    <version>19.0.0</version>
    <scope>provided</scope>
</dependency>
```
```
找不到javax.annotation.Nullable的类文件
缺少 javax.annotation.Nullable

请添加
<dependency>
    <groupId>com.google.guava</groupId>
    <artifactId>guava</artifactId>
    <version>28.2-jre</version>
    <scope>provided</scope>
</dependency>

```