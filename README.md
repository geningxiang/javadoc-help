# javadoc-help

无需依赖,基于com.sun.javadoc解析源码,生成接口文档

````
    /**
     * 用户登录
     * @param userName 用户名 {@link User#getUserName()}
     * @param passWord 密码 {@link User#getPassWord()}
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public ResponseEntity<User> login(
            @Validated
            @NotEmpty String userName,
            @Validated
            @NotBlank
            @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
                    String passWord) {
        ......
    }
````

### [查看Demo](http://panda007.gitee.io/static/javadoc/index.html)



### 版本说明

| 版本号 | 说明 |
| :----: | ------ |
| 1.0.0 | 完成基础解析功能 | 
| 1.1.0 | 添加泛型解析，新增解析结果的实体类(后续将要持久化) |
| 1.1.1 | 定义插件，解析各种注解(lombok、jsr-305) | 
| 2.0.0 | 重写整个javadoc解析,优化界面 |

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