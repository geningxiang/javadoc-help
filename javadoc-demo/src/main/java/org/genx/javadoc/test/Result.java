package org.genx.javadoc.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description: 
 * @author genx
 * @date 2020/2/15 15:55
 */
public class Result<K extends Serializable, T extends List<Map<String, Object>>, V> {


    static class Result3 {

        public void run() {
            new Result2().run();
        }
    }

    public static void run() {
        new Result3().run();

    }


}

class Result2 {
    public void run() {
        System.out.println("result2#run");
    }
}
