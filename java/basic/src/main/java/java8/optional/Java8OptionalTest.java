package java8.optional;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * description: 测试java.util.Optional类
 *
 * @version 2017年5月4日 上午9:23:38
 * @see modify content------------author------------date
 */
public class Java8OptionalTest {

	@Test
	public void ofTest() {
		Integer value1 = null;
		Integer value2 = new Integer(10);

		// Optional.ofNullable - allows passed parameter to be null.
		Optional<Integer> a = Optional.ofNullable(value1);

		// Optional.of - throws NullPointerException if passed parameter is null
		Optional<Integer> b = Optional.of(value2);
		System.out.println(sum(a, b));
	}

	public Integer sum(Optional<Integer> a, Optional<Integer> b) {

		// Optional.isPresent - checks the value is present or not

		System.out.println("First parameter is present: " + a.isPresent());
		System.out.println("Second parameter is present: " + b.isPresent());

		// Optional.orElse - returns the value if present otherwise returns
		// the default value passed.
		Integer value1 = a.orElse(new Integer(0));

		// Optional.get - gets the value, value should be present
		Integer value2 = b.get();
		return value1 + value2;
	}

	@Test
	public void isPresentTest() {
		Optional<List<String>> list = Optional.ofNullable(Arrays.asList("aa", "bb"));
		list.ifPresent(var -> System.out.println(var));

		if (list.isPresent()) {
			list.get().forEach(System.out::println);
		} else {
			System.out.println("list is null");
		}
	}

	@Test
	public void orElseTest() {
		Optional<String> optional = Optional.ofNullable("123");
		System.out.println(optional.orElse(""));
		optional = Optional.ofNullable(null);
		System.out.println(optional.orElse("isEmpty!"));
	}

	/**
	 * description: 通过实现supplier接口来返回默认值
	 * 
	 * @time: 2017年5月4日 上午9:45:54
	 */
	@Test
	public void orElseGetTest() {
		Optional<String> optional = Optional.ofNullable("123");
		System.out.println(optional.orElseGet(() -> "123456"));
		optional = Optional.ofNullable(null);
		optional.orElseGet(() -> {
		    System.out.println("parameter is null, will return 123!");
		    return "123";
		});
	}

	@Test
	public void orElseThrowTest() {
		Stream<String> names = Stream.of("Lamurudu", "Okanbi", "Oduduwa");
		Optional<String> longest = names.filter(name -> name.equals("dimu")).findFirst();

		System.out.println(longest.orElseThrow(NoSuchElementException::new));
	}
	
	@Test
	public void mapTest() {
		Optional<String> name = Optional.of("abc");
		Optional<String> upperName = name.map((value) -> value.toUpperCase());
		System.out.println(upperName.orElse("No value found"));
	}
	
	/**
	 * description: flatmap返回类型必须是Optional<T>对象，而map可以返回任意对象
	 * @time: 2017年5月4日 上午10:10:01
	 */
	@Test
	public void flatMapTest() {
		Optional<String> name = Optional.of("abc");
//		Optional<String> upperName = name.map((value) -> value.toUpperCase());
		Optional<String> upperName = name.flatMap(x->Optional.of(x.toUpperCase()));
		System.out.println(upperName.orElse("No value found"));
	}
}
