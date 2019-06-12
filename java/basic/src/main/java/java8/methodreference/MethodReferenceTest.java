package java8.methodreference;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Supplier;

import org.junit.Test;

import java8.Person;

/**
 * description: java的method reference用在 Reference to a static method
 * *ContainingClass::staticMethodName* **访问类的静态方法** Reference to a constructor
 * *ClassName::new* **调用类的构造函数** Reference to an instance method of a particular
 * object *containingObject::instanceMethodName* **调用实例对象的实例方法** Reference to an
 * instance method of an arbitrary object of a particular type
 * *ContainingType::methodName* **特定类型的实例方法**
 *
 * @version 2017年5月2日 下午1:50:39
 * @see modify content------------author------------date
 */
public class MethodReferenceTest {

	public static void main(String[] args) {
		List<String> names = new ArrayList<String>();

		names.add("Mahesh");
		names.add("Suresh");
		names.add("Ramesh");
		names.add("Naresh");
		names.add("Kalpesh");

		names.forEach(System.out::println);
		names.forEach(StringUtils::toUpper);

		Convert<Integer, String> converter = String::valueOf;
		String to = converter.convert(123);
		System.out.println(to + 1);

		// 等价于lambda (String str, Integer index) -> {return str.charAt(index);}
		// Reference to an instance method of an arbitrary object of a
		// particular type
		BiFunction<String, Integer, Character> biFunction = String::charAt;
		char c = biFunction.apply("123", 2);
		System.out.println(c);

		// 上例的lambda写法
		biFunction = (String str, Integer index) -> {
			return str.charAt(index);
		};
		c = biFunction.apply("123", 1);
		System.out.println(c);

		// BiFunction<String, Integer, String> subBiFunction =
		// String::substring;
		BiFunction<String, Integer, String> subBiFunction = String::substring;
		System.out.println(subBiFunction.apply("appleAndPear", 8));

	}

	/**
	 * description: list按照岁数排序功能，不同的实现方式
	 * 
	 * @time: 2017年5月3日 上午10:10:50
	 */
	@Test
	public void methodReferenceTestByPerson() {
		List<Person> roster = Person.createRoster();
		roster.forEach(System.out::println);

		// 实现functional interface Comparator
		Person[] rosterAsArray = roster.toArray(new Person[roster.size()]);
		Arrays.sort(rosterAsArray, new PersonAgeComparator());
		roster.forEach(System.out::println);

		// lambda expression 实现functional interface
		Arrays.sort(rosterAsArray, (Person a, Person b) -> {
			return a.getBirthday().compareTo(b.getBirthday());
		});
		roster.forEach(System.out::println);

		// 调用已经存在的方法
		Arrays.sort(rosterAsArray, (a, b) -> Person.compareByAge(a, b));
		roster.forEach(System.out::println);

		// 直接转化成方法引用 等价于上面方法
		Arrays.sort(rosterAsArray, Person::compareByAge);
		roster.forEach(System.out::println);

		// 对象的实例方法调用
		ComparisonProvider myComparisonProvider = new ComparisonProvider();
		Arrays.sort(rosterAsArray, myComparisonProvider::compareByName);
		// Arrays.sort(rosterAsArray, ComparisonProvider::compareByName);

		// 如何去查找的List实例化对象？
		BiFunction<List<Person>, Integer, Person> biFunction = List::get;
		System.out.println(biFunction.apply(roster, 1));
		
		//lambda 表达式方式调用
		Set<Person> rosterSetLambda = transferElements(roster, () -> { return new HashSet<>(); });
		System.out.println("-----lambda-------");
		rosterSetLambda.forEach(a-> System.out.println(a.getName()));
		
		//构造函数方式调用
		rosterSetLambda = transferElements(roster, HashSet :: new);
		System.out.println("------隐式类型构造函数调用------");
		rosterSetLambda.forEach(a-> System.out.println(a.getName()));
		
		rosterSetLambda = transferElements(roster, HashSet<Person> :: new);
		System.out.println("------显示类型构造函数调用------");
		rosterSetLambda.forEach(a-> System.out.println(a.getName()));
	}

	//将一个对象拷贝到另外一个对象, supplier函数式接口返回一个DEST类型集合
	public static <T, SOURCE extends Collection<T>, DEST extends Collection<T>> DEST transferElements(
			SOURCE sourceCollection, Supplier<DEST> collectionFactory) {

		DEST result = collectionFactory.get();
		for (T t : sourceCollection) {
			result.add(t);
		}
		return result;
	}

	class PersonAgeComparator implements Comparator<Person> {
		public int compare(Person a, Person b) {
			return a.getBirthday().compareTo(b.getBirthday());
		}
	}

	/**
	 * description: Person Comparison Provider 提供了两种比较方式
	 *
	 * @version 2017年5月3日 上午10:30:49
	 * @see modify content------------author------------date
	 */
	class ComparisonProvider {

		// 根据名字比较
		public int compareByName(Person a, Person b) {
			return a.getName().compareTo(b.getName());
		}

		// 根据年纪比较
		public int compareByAge(Person a, Person b) {
			return a.getBirthday().compareTo(b.getBirthday());
		}
	}
}

class StringUtils {

	/**
	 * description: 静态方法
	 * 
	 * @param param
	 *            参数
	 * @time: 2017年5月2日 下午1:57:31
	 */
	public static void toUpper(String param) {
		System.out.println(param.toUpperCase());
	}
}
