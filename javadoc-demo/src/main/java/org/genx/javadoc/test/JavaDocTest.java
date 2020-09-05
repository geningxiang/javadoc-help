package org.genx.javadoc.test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 这个类演示了文档注释
 * 测试一下内嵌锚点{@linkplain Object#toString() 还可以再内嵌吗{@linkplain Integer#toHexString(int)}}
 * @author Ayan Amhed
 * @version 1.2
 */
public class JavaDocTest {

    /**
     * 我是字段 id
     * @see Integer
     */
    private Integer id;

    /**
     * 我是字段 msg
     * {@linkplain StringBuilder#toString()}
     */
    private String msg;

    /**
     * This method returns the square of num.
     * This is a multiline description. You can use
     * as many lines as you like.
     * 测试一下内嵌锚点{@linkplain Object#toString() 还可以再内嵌吗{@linkplain Integer#toHexString(int)}}
     * @param num The value to be squared.
     * @return num squared.
     */
    public double square(double num) {
        return num * num;
    }

    /**
     * This method inputs a number from the user.
     * @return The value input as a double.
     * @exception IOException On input error.
     * @see IOException
     */
    public double getNumber() throws IOException {
        InputStreamReader isr = new InputStreamReader(System.in);
        BufferedReader inData = new BufferedReader(isr);
        String str;
        str = inData.readLine();
        return (new Double(str)).doubleValue();
    }

    /**
     * This method demonstrates square().
     * @param args Unused.
     * @return Nothing.
     * @exception IOException On input error.
     * @see IOException
     */
    public static void main(String[] args) throws IOException {
        JavaDocTest ob = new JavaDocTest();
        double val;
        System.out.println("Enter value to be squared: ");
        val = ob.getNumber();
        val = ob.square(val);
        System.out.println("Squared value is " + val);
    }
}
