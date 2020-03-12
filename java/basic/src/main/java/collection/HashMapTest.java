package collection;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HashMapTest {

    @Test
    public void hashmapTest() {
        Map<String, Integer>  map = new HashMap<String, Integer>(9,(float)0.75);

        //初始化table，重新计算阈值，tablesize 16， threshold 12
        map.put("num1", 11);
        map.put("num2", 12);
        map.put("num3", 13);
        map.put("num4", 14);
        map.put("num5", 15);
        map.put("num6", 16);
        map.put("num7", 17);
        map.put("num8", 18);
        map.put("num9", 19);
        map.put("num9", 22);
        map.put("num10", 20);
        map.put("num11", 20);
        map.put("num12", 20);

        //超过阈值，需要扩容，扩容至32，threshold为24
        map.put("num13", 20);

    }

    /**
     * return tablesize for hashmap
     */
    @Test
    public void testTableSizeforCapacity() {
        int n = 9 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        System.out.println(n+1);
    }
}
