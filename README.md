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
| 1.1.1 | 定义插件，解析各种注解(lombok、jsr-305) | 
| 1.1.2 | 上传maven不太熟练，不知道怎么删除，只好升个版本号 |

### MAVEN
最新版已上传至maven中央库
```
<plugin>
    <groupId>com.github.geningxiang</groupId>
    <artifactId>javadoc-mvn-plugin</artifactId>
    <version>1.1.2</version>
</plugin>
```
请先对项目做一次package
```
mvn javadoc-mvn:javaDoc
```