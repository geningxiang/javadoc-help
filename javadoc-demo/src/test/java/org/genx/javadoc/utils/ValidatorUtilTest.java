package org.genx.javadoc.utils;

import org.genx.javadoc.entity.User;

import javax.validation.constraints.NotBlank;

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