package json;


import com.google.gson.Gson;
import org.junit.Test;

/**
 * gson parser
 *
 * @author dwx
 * @date 2020-09-08
 */
public class GsonTest {

    @Test
    public void parseJsonString() {
//        String source = "{\"age\":12}";
        String source = "{\"age\":12, \"other\": \"xx\"}";
        Gson gson = new Gson();
        TestVO  ob = gson.fromJson(source, TestVO.class);
        System.out.println(ob.getAge());
        System.out.println(null == ob.getMap());
        System.out.println(ob.getMap());
        System.out.println(ob.getName());
    }
}
