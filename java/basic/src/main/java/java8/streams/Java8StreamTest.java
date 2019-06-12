package java8.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * description: java8 流式操作（链式操作）
 *
 * @version 2017年5月3日 下午4:56:58
 * @see
 * modify content------------author------------date
 */
public class Java8StreamTest {

	public static void main(String args[]) {
		System.out.println("Using Java 7: ");

		// Count empty strings
		List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
		System.out.println("List: " + strings);
		long count = getCountEmptyStringUsingJava7(strings);

		System.out.println("Empty Strings: " + count);
		count = getCountLength3UsingJava7(strings);

		System.out.println("Strings of length 3: " + count);

		// Eliminate empty string
		List<String> filtered = deleteEmptyStringsUsingJava7(strings);
		System.out.println("Filtered List: " + filtered);

		// Eliminate empty string and join using comma.
		String mergedString = getMergedStringUsingJava7(strings, ", ");
		System.out.println("Merged String: " + mergedString);
		List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);

		// get list of square of distinct numbers
		List<Integer> squaresList = getSquares(numbers);
		System.out.println("Squares List: " + squaresList);
		List<Integer> integers = Arrays.asList(1, 2, 13, 4, 15, 6, 17, 8, 19);

		System.out.println("List: " + integers);
		System.out.println("Highest number in List : " + getMax(integers));
		System.out.println("Lowest number in List : " + getMin(integers));
		System.out.println("Sum of all numbers : " + getSum(integers));
		System.out.println("Average of all numbers : " + getAverage(integers));
		System.out.println("Random Numbers: ");

		// print ten random numbers
		Random random = new Random();

		for (int i = 0; i < 10; i++) {
			System.out.println(random.nextInt());
		}

		System.out.println("Using Java 8: ");
		System.out.println("List: " + strings);

		//返回空字符串计数
		count = strings.stream().filter(string -> string.isEmpty()).count();
		System.out.println("Empty Strings: " + count);

		//返回字符串长度为3的个数
		count = strings.stream().filter(string -> string.length() == 3).count();
		System.out.println("Strings of length 3: " + count);

		//去除空字符串
		filtered = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());
		System.out.println("Filtered List: " + filtered);

		//去空后，串联字符串
		mergedString = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.joining(", "));
		System.out.println("Merged String: " + mergedString);

		//去重后返回字符串
		squaresList = numbers.stream().map(i -> i * i).distinct().collect(Collectors.toList());
		System.out.println("Squares List: " + squaresList);
		System.out.println("List: " + integers);

		//统计最大与最小值等
		IntSummaryStatistics stats = integers.stream().mapToInt((x) -> x).summaryStatistics();

		System.out.println("Highest number in List : " + stats.getMax());
		System.out.println("Lowest number in List : " + stats.getMin());
		System.out.println("Sum of all numbers : " + stats.getSum());
		System.out.println("Average of all numbers : " + stats.getAverage());
		System.out.println("Random Numbers: ");

		//排序操作
		random.ints().limit(10).sorted().forEach(System.out::println);

		// parallel processing
		count = strings.parallelStream().filter(string -> string.isEmpty()).count();
		System.out.println("Empty Strings: " + count);
	}

	private static int getCountEmptyStringUsingJava7(List<String> strings) {
		int count = 0;

		for (String string : strings) {

			if (string.isEmpty()) {
				count++;
			}
		}
		return count;
	}

	private static int getCountLength3UsingJava7(List<String> strings) {
		int count = 0;

		for (String string : strings) {

			if (string.length() == 3) {
				count++;
			}
		}
		return count;
	}

	private static List<String> deleteEmptyStringsUsingJava7(List<String> strings) {
		List<String> filteredList = new ArrayList<String>();

		for (String string : strings) {

			if (!string.isEmpty()) {
				filteredList.add(string);
			}
		}
		return filteredList;
	}

	private static String getMergedStringUsingJava7(List<String> strings, String separator) {
		StringBuilder stringBuilder = new StringBuilder();

		for (String string : strings) {

			if (!string.isEmpty()) {
				stringBuilder.append(string);
				stringBuilder.append(separator);
			}
		}
		String mergedString = stringBuilder.toString();
		return mergedString.substring(0, mergedString.length() - 2);
	}

	private static List<Integer> getSquares(List<Integer> numbers) {
		List<Integer> squaresList = new ArrayList<Integer>();

		for (Integer number : numbers) {
			Integer square = new Integer(number.intValue() * number.intValue());

			if (!squaresList.contains(square)) {
				squaresList.add(square);
			}
		}
		return squaresList;
	}

	private static int getMax(List<Integer> numbers) {
		int max = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {

			Integer number = numbers.get(i);

			if (number.intValue() > max) {
				max = number.intValue();
			}
		}
		return max;
	}

	private static int getMin(List<Integer> numbers) {
		int min = numbers.get(0);

		for (int i = 1; i < numbers.size(); i++) {
			Integer number = numbers.get(i);

			if (number.intValue() < min) {
				min = number.intValue();
			}
		}
		return min;
	}

	private static int getSum(List numbers) {
		int sum = (int) (numbers.get(0));

		for (int i = 1; i < numbers.size(); i++) {
			sum += (int) numbers.get(i);
		}
		return sum;
	}
	
	/**
	 * filter and distict function to select the unique element
	 */
	@Test
	public void streamFliterAndCollect() {
	    List<Integer> nums = Arrays.asList(1,2,3,4,2,4,5,6);
	    nums.stream().filter(item -> item % 2 == 0)
	                .distinct().forEach(System.out::print);
	    System.out.println();
	    nums.stream().filter(item -> item % 2 == 0)
            .forEach(System.out::print);
	    System.out.println();
	}
	
	/**
	 * test ordered stream and unordered stream difference
	 */
	@Test
	public void orderedAndUnOrderedStream() {
	    System.out.println("ordered and unordered test！");
	    List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9);
	    list.stream().limit(3).forEach(System.out::println);
	    Set<Integer> set = new HashSet<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
	    set.stream().limit(3).forEach(System.out::println);
	}
	
	/**
	 * data filter and construct
	 */
	@Test
	public void flatMap() {
	    List<Integer> num1 = Arrays.asList(1,2,3);
	    List<Integer> num2 = Arrays.asList(3,4);
	    List<int[]> pairs = num1.stream().flatMap(i -> 
	                                                num2.stream()
	                                                    .filter(j -> (i +j) % 3 == 0)
	                                                    .map(j -> new int[] {i, j})
	                                             )
	                                    .collect(Collectors.toList());
	    pairs.forEach(item -> { 
	            for (int i = 0; i < item.length; i++) {
	                System.out.println(item[i]);
	            }
	    });
	}
	
	/**
	 * search the greek mathematician pythagoras between 1 and 100
	 */
	@Test
	public void buildPythagoreanTriples() {
	    Stream<double[]> pythagoreanTriple = IntStream.rangeClosed(1, 100).boxed()
	                                        .flatMap(a ->
	                                            IntStream.rangeClosed(a, 100)
	                                            .mapToObj(b -> new double[] {a, b, Math.sqrt(a*a + b*b)})
	                                            .filter(t -> t[2] % 1 == 0));
	    pythagoreanTriple.forEach(t -> System.out.println(t[0] + "," + t[1] + "," + t[2]));
	}
	
	/**
	 * produce infinite num 
	 */
	@Test
	public void generateStreamFromInfiniteNum() {
	    System.out.println("produce even number");
	    Stream.iterate(0, n -> n+2)
	        .limit(10)
	        .forEach(System.out::println);
	    
	    System.out.println("produce Fibonacci series");
	    Stream.iterate(new int[] {0,1}, t-> new int[] {t[1], t[0]+t[1]})
	        .limit(20)
	        .forEach(t -> System.out.println("(" + t[0] + "," + t[1] + ")"));
	}

	private static int getAverage(List<Integer> numbers) {
		return getSum(numbers) / numbers.size();
	}
}
