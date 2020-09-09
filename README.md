# javadoc-help

#### 先来看一段代码
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

#### 再来看看效果图
![](https://panda007.gitee.io/static/javadoc/demo.png)

### [Demo1](https://panda007.gitee.io/static/javadoc/index.html)

### [Demo2 for macrozheng/mall-portal ](https://panda007.gitee.io/static/javadoc/mall-portal/index.html)

### [Demo3 for macrozheng/mall-admin ](https://panda007.gitee.io/static/javadoc/mall-admin/index.html)



### 版本说明

| 版本号 | 说明 |
| :----: | ------ |
| 1.0.0 | 完成基础解析功能 |
| 1.1.0 | 添加泛型解析，新增解析结果的实体类(后续将要持久化) |
| 1.1.1 | 定义插件，解析各种注解(lombok、jsr-305) |
| 2.0.0 | 重写整个javadoc解析,优化界面 |

### MAVEN
maven中央仓库更新太麻烦,还没能上传新版本
```
<plugin>
    <groupId>com.github.geningxiang</groupId>
    <artifactId>javadoc-mvn-plugin</artifactId>
    <version>1.1.2</version>
</plugin>
```
````
<plugins>
    <plugin>
        <groupId>com.github.geningxiang</groupId>
        <artifactId>javadoc-mvn-plugin</artifactId>
        <version>2.0.0-SNAPSHOT</version>
        <configuration>
            <!-- 读取关联依赖的源码 -->
            <importSourceDirs>
                <importSourceDir>${basedir}/../mall-common/</importSourceDir>
                <importSourceDir>${basedir}/../mall-mbg/</importSourceDir>
                <importSourceDir>${basedir}/../mall-search/</importSourceDir>
                <importSourceDir>${basedir}/../mall-security/</importSourceDir>
            </importSourceDirs>
        </configuration>
    </plugin>
</plugins>
````

运行插件前需要对项目先做一次package
```
mvn clean package -Dmaven.test.skip=true

mvn javadoc-mvn:javaDoc
```

###规划中
- 在页面上能够跳转到关联注释 @see | {@link} | {@linkPlan}
- JavaDoc、RestApiDoc对象序列化后向配置的接口地址发送
- 与 [automate2](https://gitee.com/panda007/automate2) 结合,作为一个独立的JAVA接口管理模块;  
  为项目相关人员实时推送Java接口变动及Rest接口变动,并提供版本之间的差异比较功能

