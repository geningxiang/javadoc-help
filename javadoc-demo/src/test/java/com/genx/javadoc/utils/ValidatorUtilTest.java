package com.genx.javadoc.utils;

import com.genx.javadoc.entity.User;
import org.hibernate.validator.internal.engine.ValidationContext;

import javax.validation.constraints.NotBlank;

import java.lang.reflect.Executable;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author: genx
 * @date: 2019/3/15 17:17
 */
public class ValidatorUtilTest {

    public static void main(String[] args) {

        @NotBlank
        String userName = null;


        User user = new User();

        ValidatorUtil.validate(user);


    }

}