package guava.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.HashMultiset;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multiset;
import com.google.common.collect.Table;

/**
 * description: research about guava collections
 *
 * @version 2017年4月26日 上午9:04:51
 * @see modify content------------author------------date
 */
public class GuavaCollections {

	/**
	 * description: 改变传统的map value只有1个值情况， value可以对应多个值
	 * 
	 * @time: 2017年4月26日 上午9:24:58
	 */
	@Test
	public void multimapTest() {
		ListMultimap<String, String> lmm = ArrayListMultimap.create();
		lmm.put("fruit", "apple");
		lmm.put("fruit", "banana");
		lmm.put("sport", "basketball");
		lmm.put("sport", "volleyball");
		lmm.put("sport", "football");
		lmm.put("sport", "football");

		for (String key : lmm.keySet()) {
			List<String> list = lmm.get(key);
			for (String item : list) {
				System.out.println(key + ":" + item);
			}
		}
	}

	/**
	 * description: bimap能够通过inverse方法返回从value-key的关系映射
	 * 从而达到通过value查找key的目的，biMap与反转后的map共享双向视图关系， 两边的任何增删改都会影响双边的值
	 * 
	 * @time: 2017年4月26日 上午8:58:27
	 */
	@Test
	public void biMapTest() {
		BiMap<Integer, String> biMap = HashBiMap.create();
		biMap.put(1, "Lily");
		biMap.put(2, "Candy");
		biMap.put(3, "Lucy");

		System.out.println(biMap);

		BiMap<String, Integer> inverMap = biMap.inverse();
		System.out.println(inverMap);

		// biMap value must be different, otherwise will throw
		// java.lang.IllegalArgumentException
		// biMap.put(4, "Lucy");

		// forcePut method will overwrite the previous value
		biMap.forcePut(4, "Lucy");
		System.out.println(biMap);
		System.out.println(inverMap);

		inverMap.remove("Candy");
		System.out.println(biMap);
		System.out.println(inverMap);

	}

	/**
	 * description: table<Row, Column, Value>数据类型，等价于Map<Row, Map<Column,Value>>
	 * 
	 * @time: 2017年4月26日 上午9:28:44
	 */
	@Test
	public void tableTest() {
		// create a table
		Table<String, String, String> employeeTable = HashBasedTable.create();

		// initialize the table with employee details
		employeeTable.put("IBM", "101", "Mahesh");
		employeeTable.put("IBM", "102", "Ramesh");
		employeeTable.put("IBM", "103", "Suresh");

		employeeTable.put("Microsoft", "111", "Sohan");
		employeeTable.put("Microsoft", "112", "Mohan");
		employeeTable.put("Microsoft", "113", "Rohan");

		employeeTable.put("TCS", "121", "Ram");
		employeeTable.put("TCS", "122", "Shyam");
		employeeTable.put("TCS", "123", "Sunil");

		// get Map corresponding to IBM
		Map<String, String> ibmEmployees = employeeTable.row("IBM");

		System.out.println("List of IBM Employees");

		for (Map.Entry<String, String> entry : ibmEmployees.entrySet()) {
			System.out.println("Emp Id: " + entry.getKey() + ", Name: " + entry.getValue());
		}

		// get all the unique keys of the table
		Set<String> employers = employeeTable.rowKeySet();
		System.out.print("Employers: ");

		for (String employer : employers) {
			System.out.print(employer + " ");
		}

		System.out.println();

		// get a Map corresponding to 102
		Map<String, String> EmployerMap = employeeTable.column("102");

		for (Map.Entry<String, String> entry : EmployerMap.entrySet()) {
			System.out.println("Employer: " + entry.getKey() + ", Name: " + entry.getValue());
		}
	}

	/*
	 * multi set 可以同时存放相同的元素
	 */
	@Test
	public void multiSetTest() {
		// create a multiset collection
		Multiset<String> multiset = HashMultiset.create();

		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("d");
		multiset.add("a");
		multiset.add("b");
		multiset.add("c");
		multiset.add("b");
		multiset.add("b");
		multiset.add("b");

		// print the occurrence of an element
		System.out.println("Occurrence of 'b' : " + multiset.count("b"));

		// print the total size of the multiset
		System.out.println("Total Size : " + multiset.size());

		// get the distinct elements of the multiset as set
		Set<String> set = multiset.elementSet();

		// display the elements of the set
		System.out.println("Set [");

		for (String s : set) {
			System.out.println(s);
		}

		System.out.println("]");

		// display all the elements of the multiset using iterator
		Iterator<String> iterator = multiset.iterator();
		System.out.println("MultiSet [");

		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}

		System.out.println("]");

		// display the distinct elements of the multiset with their occurrence
		// count
		System.out.println("MultiSet [");

		for (Multiset.Entry<String> entry : multiset.entrySet()) {
			System.out.println("Element: " + entry.getElement() + ", Occurrence(s): " + entry.getCount());
		}
		System.out.println("]");

		// remove extra occurrences
		multiset.remove("b", 2);

		// print the occurrence of an element
		System.out.println("Occurence of 'b' : " + multiset.count("b"));
	}

}
