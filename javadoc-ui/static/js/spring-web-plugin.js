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
                        parameters: methodDoc.params
                    });
                }

            }


        }
        return apiData;
    };


    window.SpringWebPlugin = {
        "read": read
    };

}(window);