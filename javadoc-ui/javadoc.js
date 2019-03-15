var javadoc = {
  "com.genx.javadoc.entity.User": {
    "annotations": {},
    "className": "com.genx.javadoc.entity.User",
    "methods": [
      {
        "annotations": {},
        "comment": "",
        "methodName": "getId",
        "params": [],
        "returnType": "java.lang.Long",
        "throwExpections": {}
      },
      {
        "annotations": {},
        "comment": "",
        "methodName": "setId",
        "params": [
          {
            "annotations": {},
            "className": "java.lang.Long",
            "name": "id"
          }
        ],
        "returnType": "void",
        "throwExpections": {}
      },
      {
        "annotations": {},
        "comment": "",
        "methodName": "getUserName",
        "params": [],
        "returnType": "java.lang.String",
        "throwExpections": {}
      },
      {
        "annotations": {},
        "comment": "",
        "methodName": "setUserName",
        "params": [
          {
            "annotations": {},
            "className": "java.lang.String",
            "name": "userName"
          }
        ],
        "returnType": "void",
        "throwExpections": {}
      },
      {
        "annotations": {},
        "comment": "",
        "methodName": "getPassWord",
        "params": [],
        "returnType": "java.lang.String",
        "throwExpections": {}
      },
      {
        "annotations": {},
        "comment": "",
        "methodName": "setPassWord",
        "params": [
          {
            "annotations": {},
            "className": "java.lang.String",
            "name": "passWord"
          }
        ],
        "returnType": "void",
        "throwExpections": {}
      }
    ],
    "rawCommentText": " Created with IntelliJ IDEA.\n Description:\n\n @author: genx\n @date: 2019/3/13 23:32\n"
  },
  "com.genx.javadoc.controller.IndexController": {
    "annotations": {
      "org.springframework.web.bind.annotation.RequestMapping": {
        "data": {
          "value": [
            "/api"
          ]
        }
      },
      "org.springframework.web.bind.annotation.RestController": {
        "data": {}
      }
    },
    "className": "com.genx.javadoc.controller.IndexController",
    "methods": [
      {
        "annotations": {
          "org.springframework.web.bind.annotation.RequestMapping": {
            "data": {
              "method": [
                "org.springframework.web.bind.annotation.RequestMethod.GET"
              ],
              "produces": [
                "application/json;charset=UTF-8"
              ],
              "value": [
                "/login"
              ]
            }
          }
        },
        "comment": "登录",
        "methodName": "login",
        "params": [
          {
            "annotations": {
              "javax.validation.constraints.NotEmpty": {
                "data": {
                  "message": [
                    "{javax.validation.constraints.NotEmpty.message}"
                  ]
                }
              }
            },
            "className": "java.lang.String",
            "comment": "用户名",
            "name": "userName"
          },
          {
            "annotations": {
              "javax.validation.constraints.Pattern": {
                "data": {
                  "regexp": [
                    "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/"
                  ],
                  "message": [
                    "密码必须是6~10位数字和字母的组合"
                  ]
                }
              },
              "javax.validation.constraints.NotBlank": {
                "data": {
                  "message": [
                    "{javax.validation.constraints.NotBlank.message}"
                  ]
                }
              }
            },
            "className": "java.lang.String",
            "comment": "密码",
            "name": "passWord"
          }
        ],
        "returnComment": "{\n \"status\": 200,\n \"msg\": \"ok\",\n \"token\": \"HINASDKBBH5123SH238\"      //令牌\n }",
        "returnType": "java.util.Map<java.lang.String, java.lang.String>",
        "throwExpections": {}
      },
      {
        "annotations": {
          "org.springframework.web.bind.annotation.RequestMapping": {
            "data": {
              "method": [
                "org.springframework.web.bind.annotation.RequestMethod.GET"
              ],
              "produces": [
                "application/json;charset=UTF-8"
              ],
              "value": [
                "/logout"
              ]
            }
          },
          "java.lang.Deprecated": {
            "data": {}
          }
        },
        "comment": "登出",
        "methodName": "logout",
        "params": [
          {
            "annotations": {},
            "className": "java.lang.String",
            "comment": "用户令牌",
            "name": "userToken"
          }
        ],
        "returnComment": "{\n \"status\": 200,\n \"msg\": \"ok\"\n }",
        "returnType": "java.util.Map<java.lang.String, java.lang.String>",
        "throwExpections": {}
      }
    ],
    "rawCommentText": " Created with IntelliJ IDEA.\n Description:\n\n @author: genx\n @date: 2019/3/12 9:42\n"
  },
  "com.genx.javadoc.controller.AppController": {
    "annotations": {
      "org.springframework.web.bind.annotation.RequestMapping": {
        "data": {
          "value": [
            "/api"
          ]
        }
      },
      "org.springframework.stereotype.Controller": {
        "data": {}
      }
    },
    "className": "com.genx.javadoc.controller.AppController",
    "methods": [
      {
        "annotations": {
          "org.springframework.web.bind.annotation.RequestMapping": {
            "data": {
              "method": [
                "org.springframework.web.bind.annotation.RequestMethod.GET"
              ],
              "produces": [
                "application/json;charset=UTF-8"
              ],
              "value": [
                "/homeInfo"
              ]
            }
          }
        },
        "comment": "获取APP首页数据",
        "methodName": "homeInfo",
        "params": [],
        "returnComment": "{\n \"status\": 200,\n \"msg\": \"ok\"\n }",
        "returnType": "java.util.Map<java.lang.String, java.lang.String>",
        "throwExpections": {}
      }
    ],
    "rawCommentText": " Created with IntelliJ IDEA.\n Description:\n app 信息\n @author: genx\n @date: 2019/3/13 23:35\n"
  }
};