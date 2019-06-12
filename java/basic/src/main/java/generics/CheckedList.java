package generics;

import typeinfo.pets.*;
import java.util.*;

public class CheckedList {
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	static void oldStyleMethod(List probablyDogs) {
		probablyDogs.add(new Cat());
	}
	
	static void newStyleMethod(List<Dog> dogsList) {
	//	dogsList.add(new Cat()); //compile error, type check
	}

	public static void main(String[] args) {
		List<Dog> dogs1 = new ArrayList<Dog>();
//		List<Integer> test = new ArrayList<Integer>();
//		oldStyleMethod(test);
//		test.add(new Cat());
		oldStyleMethod(dogs1); // Quietly accepts a Cat
		List<Dog> dogs2 = Collections.checkedList(new ArrayList<Dog>(), Dog.class);
		try {
			oldStyleMethod(dogs2); // Throws an exception
		} catch (Exception e) {
			System.out.println(e);
		}
		// Derived types work fine:
		List<Pet> pets = Collections.checkedList(new ArrayList<Pet>(), Pet.class);
		pets.add(new Dog());
		pets.add(new Cat());
	}
}
