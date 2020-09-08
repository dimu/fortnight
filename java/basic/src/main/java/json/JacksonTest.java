package json;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;

/**
 * jackson 解析测试
 *
 * @author dwx
 * @date 2020-09-08
 */
public class JacksonTest {

    @Test
    public void parseJsonString() throws IOException {
//        String source = "{\"age\":12}";
        /**
         * 如果字段不是实体类的子集，会报错
         */
        String source = "{\"age\":12, \"other\": \"xx\"}";
        ObjectMapper om = new ObjectMapper();
        TestVO  ob = om.readValue(source, TestVO.class);
        System.out.println(ob.getAge());
        System.out.println(null == ob.getMap());
        System.out.println(ob.getMap());
        System.out.println(ob.getName());
    }
}
