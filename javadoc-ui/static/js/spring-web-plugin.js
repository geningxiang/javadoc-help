!function (window) {
    var springUtils = {
        getRequestMapPath: function (annotations) {
            var paths = this.getRequestMapPaths(annotations);
            return paths.length > 0 ? paths[0] : '';

        },
        getRequestMapPaths: function (annotations) {
            if (annotations) {
                var value = annotations['org.springframework.web.bind.annotation.RequestMapping'];
                if (value && value.data && value.data.value && value.data.value.length > 0) {
                    return value.data.value;
                }
            }
            return [];
        },
        isControllerClass: function (classDoc) {
            return classDoc && classDoc.annotations && (classDoc.annotations['org.springframework.web.bind.annotation.RestController'] || classDoc.annotations['org.springframework.stereotype.Controller']);
        },
        isControllerMethod: function (methodDoc) {
            return methodDoc && methodDoc.annotations && methodDoc.annotations['org.springframework.web.bind.annotation.RequestMapping'];
        },
        getApiMethods: function (methodDoc) {
            if (methodDoc && methodDoc.annotations) {
                var value = methodDoc.annotations['org.springframework.web.bind.annotation.RequestMapping'];
                if (value && value.data && value.data.method && value.data.method.length > 0) {
                    for (var i = 0; i < value.data.method.length; i++) {
                        value.data.method[i] = value.data.method[i].replace('org.springframework.web.bind.annotation.RequestMethod.', '');
                    }
                    return value.data.method;
                }
            }
            return [];
        },
        getParameters: function(methodDoc){
            var parameters = [];
            for (var i = 0; methodDoc.params && i < methodDoc.params.length; i++) {
                if(this.isParameterShow(methodDoc.params[i])){
                    parameters.push(methodDoc.params[i]);
                }
            }
            return parameters;
        },
        isParameterShow: function(parameter){
            if(parameter.className == 'javax.servlet.http.HttpServletResponse' || parameter.className == 'javax.servlet.http.HttpServletRequest' || parameter.className == 'org.springframework.ui.ModelMap'){
                return false;
            }
            return true;
        }

    };


    var read = function (data) {
        var apiData = [];
        var classDoc;
        for (var key in data) {
            classDoc = data[key];

            if (springUtils.isControllerClass(classDoc)) {

                var basePath = springUtils.getRequestMapPath(classDoc.annotations);

                var methodDoc;
                for (var i = 0; classDoc.methods && i < classDoc.methods.length; i++) {
                    methodDoc = classDoc.methods[i];

                    var methodPath = springUtils.getRequestMapPath(methodDoc.annotations);

                    if(methodPath == ''){
                        continue;
                    }

                    apiData.push({
                        path: basePath + methodPath,
                        methods: springUtils.getApiMethods(methodDoc),
                        description: methodDoc.comment,
                        className: classDoc.className,
                        methodName: methodDoc.methodName,
                        returnType: methodDoc.returnType,
                        returnComment: methodDoc.returnComment,
                        parameters: springUtils.getParameters(methodDoc)
                    });
                }

            }


        }
        return apiData;
    };
    var parseTree = function(apiArray, depth){
        depth = depth || 3; //默认遍历到3层
        var treeMap = {};
        for (var i = 0; i < apiArray.length; i++) {
            var item = apiArray[i];
            var ss = item.path.split('/');

            var data = treeMap;
            var currentDepth = 0;
            for (var j = 0; j < ss.length; j++) {
                if(ss[j]){
                    currentDepth++;

                    if(j == ss.length - 1 || currentDepth >= depth){
                        data[ss.slice(j).join("/")] = null;
                        break;
                    } else {
                        if(!data[ss[j]]) {
                            data[ss[j]] = {};
                        }
                        data = data[ss[j]];
                    }

                }
            }
        }
        return treeMap;
    };


    window.SpringWebPlugin = {
        "read": read,
        "parseTree": parseTree,
    };

}(window);