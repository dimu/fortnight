package containers;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ListToArrayTest {

	@Test
	public void listToArray() {
		List<Person> list = new ArrayList<Person>();
		
		list.add(new Person("dd",11));
		list.add(new Person("ss", 12));
		
		System.out.println(list.toArray().toString());
	}
}
