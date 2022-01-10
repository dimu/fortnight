package containers;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * flatmap作用,flatmap将Stream<R>类型的数据平铺开来，如多个集合合并成一个集合
 */
public class FlatMapTest {

    public static void main(String[] args) {
        System.out.println("compile test!");
    }

    @Test
    public void flatMapTest() {
        List<List<String>> listList = new ArrayList<>();
        List<String> aList = Arrays.asList("a","b","c");
        List<String> bList =Arrays.asList("b","c", "d","e");
        listList.add(aList);
        listList.add(bList);
        System.out.println(listList.stream().flatMap(item->item.parallelStream()).distinct().collect(Collectors.joining("-")));
    }
}
