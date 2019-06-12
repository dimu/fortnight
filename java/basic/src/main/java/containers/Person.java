package containers;



public class Person {
	
	private String name;
	
	private int age;

	public Person() {
		
	}
	
	public Person(String string, int i) {
		this.name = string;
		this.age = i;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}
	
}
