package com.genx.javadoc.entity;

import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/15 14:54
 */

public class UserTest {

    @Test
    public void test() {

        User.UserBuilder builder = new User.UserBuilder();

        User user = builder.setId(1L).build();

        System.out.println(user.getId());
    }

}
