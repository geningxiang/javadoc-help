package com.genx.javadoc;

import com.genx.javadoc.vo.ClassDocVO;
import com.sun.javadoc.ClassDoc;
import com.sun.javadoc.FieldDoc;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: genx
 * @date: 2019/3/9 18:27
 */
public class JavaDocReaderTest {

    @Test
    public void read() throws IOException {

        ClassDoc[] classDocs = JavaDocReader.readByClassDoc(new File("E:\\idea-workspace\\CaimaoProject\\CaimaoBusiness\\src\\com\\caimao\\constants/CapitalInOutConstants.java"), Arrays.asList("E:\\idea-workspace\\CaimaoProject\\CaimaoBusiness\\target\\classes"));


        ClassDoc c = classDocs[0];

        Map<String, String> map = new HashMap<>(128);

        Map<Integer, String> treeMap = new TreeMap();

        for (FieldDoc field : c.fields()) {

            map.put(field.name(), String.valueOf(field.constantValue()));
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            if(!entry.getKey().startsWith("INT_")){
                String intValue = map.get("INT_"+entry.getKey());

                if(intValue != null){
                    int a = Integer.parseInt(intValue);
//                    treeMap.put(a, "/**\n" +
//                            "     * "+entry.getValue()+"\n" +
//                            "     */"+entry.getKey() + "("+a+", \""+entry.getValue()+"\", "+(a >=1000 ? "false":"true")+", "+(a >=1000 ? "false":"true")+"),");

                    treeMap.put(a, "/**\n" +
                            "     * "+entry.getValue()+"\n" +
                            "     */\nString "+entry.getKey() + " = CapitalInOutType."+entry.getKey()+".getName();\n"
                        + "int INT_" + entry.getKey() + " = CapitalInOutType."+entry.getKey()+".getType();\n"
                    );
                }
            }
        }

        for (String value : treeMap.values()) {
            System.out.println(value);
        }
    }

}