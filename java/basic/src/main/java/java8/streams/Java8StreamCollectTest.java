package java8.streams;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class Java8StreamCollectTest {

    @Test
    public void jointest() {
        System.out.println(Stream.of("one", "two", "three").collect(Collectors.joining(",")));
        System.out.println(Stream.of("two", "one", "three").sorted((t1, t2) -> t1.compareTo(t2)).collect(Collectors.joining(",")));
        Stream<String> original = Stream.of("one", "two", "three");
        System.out.println(original.collect(Collectors.joining(",")));
        System.out.println( Stream.of("one", "two", "three").filter(item -> item.startsWith("t")).collect(Collectors.joining(",")));
//        System.out.println(original.collect(Collectors.joining(","))); //运行报错，stream has already closed
    }
}
