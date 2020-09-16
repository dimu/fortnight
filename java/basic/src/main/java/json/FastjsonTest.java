package json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
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
        /**
         * fastjson自动double类型的数据转化为BigDecimal，而bigdecimal被存储至mongodb会变成string
         */

        int disableDecimalFeature = JSON.DEFAULT_PARSER_FEATURE & ~Feature.UseBigDecimal.getMask();

        String source = "{\"age\":12, \"other\": \"xx\", \"map\":16.54, \"id\": \"123\"}";
        TestVO  ob = JSONObject.parseObject(source, TestVO.class, disableDecimalFeature);
        System.out.println(ob.getAge());
        System.out.println(null == ob.getMap());
        System.out.println(ob.getMap());
        System.out.println(ob.getName());
    }
}
