package collection;

import org.junit.Test;

/**
 * 自定义LRU缓存测试用例
 */
public class LRUCacheTest {

    @Test
    public void lruCaseTest() {
        LRUCache<String, String> lruCache = new LRUCache<>(2);
        lruCache.put("age", "32");
        lruCache.put("name", "camel");
        lruCache.put("sex", "male");
        lruCache.forEach( (key,value) -> System.out.println(key + ":" + value));
    }
}
