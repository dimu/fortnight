package cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import javafx.util.Duration;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

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
}
