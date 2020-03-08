package org.genx.javadoc.plugin.lombok;

import org.genx.javadoc.vo.MethodDocVO;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/3/6 22:36
 */
public class LomBokPluginTest {

    @Test
    public void test(){

        for (Method declaredMethod : MethodDocVO.class.getDeclaredMethods()) {
            System.out.println(declaredMethod);

            System.out.println(declaredMethod.getModifiers());
        }

    }

}