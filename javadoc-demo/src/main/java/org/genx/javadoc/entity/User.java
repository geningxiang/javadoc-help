package org.genx.javadoc.entity;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.Comparator;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/13 23:32
 */
@Data
public class User implements Serializable, Comparator<User> {
    /**
     * ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotEmpty
    private String userName;

    /**
     * 密码
     */
    @NotBlank
    @Pattern(regexp = "/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,10}$/", message = "密码必须是6~10位数字和字母的组合")
    private transient String passWord;

    /**
     * 用户令牌
     */
    private String userToken;

    @Override
    public int compare(User o1, User o2) {
        return 0;
    }


    static class UserBuilder {

        User user = new User();

        public UserBuilder setId(Long id) {
            user.setId(id);
            return this;
        }

        public User build() {
            return user;
        }

    }
}
