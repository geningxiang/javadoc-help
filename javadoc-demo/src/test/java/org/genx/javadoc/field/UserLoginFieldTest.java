package org.genx.javadoc.field;

import org.hibernate.validator.HibernateValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/6 22:25
 */
public class UserLoginFieldTest {

    private static final Validator validator;

    private static final String DEFAULT_TEMPLATE_STR_START = "{javax.validation.constraints.";

    static{
        //failFast：true快速失败返回模式    false 普通模式
        ValidatorFactory validatorFactory = Validation.byProvider( HibernateValidator.class )
                .configure()
                .addProperty( "hibernate.validator.fail_fast", "true" )
                .buildValidatorFactory();
        validator = validatorFactory.getValidator();
    }

    public static void validate(Object data){
        if(data != null){
            Set<ConstraintViolation<Object>> result = validator.validate(data);
            if(result.size() > 0){
                for (ConstraintViolation<Object> cv : result) {
                    StringBuilder msg = new StringBuilder(32);
                    if(cv.getMessageTemplate().startsWith(DEFAULT_TEMPLATE_STR_START)){
                        //当前使用了 默认模板
                        msg.append("[").append(cv.getPropertyPath().toString()).append("]");
                    }
                    msg.append(cv.getMessage());
                    throw new IllegalArgumentException(msg.toString());
                }
            }
        }
    }

    public static void main(String[] args) {
        UserLoginField userLoginField = new UserLoginField();

        validate(userLoginField);
    }

}