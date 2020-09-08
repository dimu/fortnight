package cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.CacheLoader;
import com.github.benmanes.caffeine.cache.Caffeine;
import javafx.util.Duration;
import org.apache.poi.ss.formula.functions.T;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 */
public class CaffeineTest {

    @Test
    public void writeTest() throws InterruptedException {
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS)
//                .refreshAfterWrite(Duration.seconds(30))
                .initialCapacity(10)
                .maximumSize(100)
                .build();
        cache.put("name", "dwx");
        System.out.println(cache.getIfPresent("name"));
        TimeUnit.SECONDS.sleep(20);
        System.out.println(cache.getIfPresent("name"));

    }

    @Test
    public void accessExpireTest() throws InterruptedException {
        AtomicInteger count = new AtomicInteger(1);
        Cache<String, String> cache = Caffeine.newBuilder().expireAfterAccess(60, TimeUnit.SECONDS)
                .refreshAfterWrite(5, TimeUnit.SECONDS)
                .initialCapacity(10)
                .maximumSize(100)
                .recordStats()
                .build(new CacheLoader<String, String>() {
                    @Nullable
                    @Override
                    public String load(@NonNull String key) throws Exception {
                        System.out.println("loadkey " + key);
                        return key + (count.incrementAndGet());
                    }

                    @Override
                    public String reload(@NonNull String key, @NonNull String oldValue) throws  Exception {
                        System.out.println("reloadkey " + key + " ;oldvalue:" + oldValue);
                        String newValue  = oldValue + count.incrementAndGet();
                        System.out.println(newValue);
                        return newValue;
                    }
                });

        cache.put("aa", "123");
//        cache.get()
        System.out.println(cache.getIfPresent("aa"));
        TimeUnit.SECONDS.sleep(7);
        System.out.println(cache.getIfPresent("aa"));
        System.out.println(cache.getIfPresent("aa"));
        System.out.println(cache.getIfPresent("bb"));

    }
}
