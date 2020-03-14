package collection;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 通过LinkedHashMap来实现LRU算法
 */
public class LRUCache<K,V> extends LinkedHashMap<K, V> {

    private int cacheSize;

    public LRUCache(int cacheSize) {
        super((int)Math.ceil(cacheSize/0.75) + 1, 0.75f, true);
        this.cacheSize = cacheSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > cacheSize;
    }
}


