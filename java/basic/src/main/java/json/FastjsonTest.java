package json;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;

/**
 * json 测试类
 *
 * @author dwx
 * @date 2020-09-08
 */
public class FastjsonTest {

    /**
     * 如果jsonstring中字符串字段少于对象字段，缺失的字段为空或者null
     */
    @Test
    public void parseJsonString() {
//        String source = "{\"age\":12}";
        String source = "{\"age\":12, \"other\": \"xx\"}";
        TestVO  ob = JSONObject.parseObject(source, TestVO.class);
        System.out.println(ob.getAge());
        System.out.println(null == ob.getMap());
        System.out.println(ob.getMap());
        System.out.println(ob.getName());
    }
}
