package com.genx.javadoc.resolver;

import org.springframework.core.MethodParameter;
import org.springframework.lang.Nullable;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.annotation.RequestParamMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.lang.annotation.Annotation;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/15 17:05
 */
public class MyRequestParamMethodArgumentResolver extends RequestParamMethodArgumentResolver {
    public MyRequestParamMethodArgumentResolver() {
        super(true);
    }

    @Override
    protected void handleResolvedValue(@Nullable Object arg, String name, MethodParameter parameter, @Nullable ModelAndViewContainer mavContainer, NativeWebRequest webRequest) {
        super.handleResolvedValue(arg, name, parameter, mavContainer, webRequest);

        System.out.println("MyRequestParamMethodArgumentResolver#handleResolvedValue");
        System.out.println(arg);
        System.out.println(name);
        for (Annotation annotation : parameter.getParameterAnnotations()) {
            System.out.println(annotation.annotationType().getName());
        }

    }
}
