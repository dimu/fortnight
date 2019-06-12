package reflect;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * description: 
 *
 * @version 2017年11月2日 上午9:25:30
 * @see
 * modify content------------author------------date
 */
public class ClassLoad {
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		System.out.println(Test.class);
		System.out.println(Class.forName(Test.class.getName()));
		Object instance = Class.forName(Test.class.getName()).newInstance();
		if (instance instanceof Test ) {
			System.out.println(((Test) instance).getAge());
			System.out.println(((Test) instance).getClassmates());
			System.out.println(((Test) instance).getCourses());
			System.out.println(((Test) instance).getScore());
		}
		
		Field[] fields = Test.class.getDeclaredFields();
		for (Field item : fields) {
			System.out.println(item.getName() + ":" + item.getType());
		}
	}
}

class Test {
	private  int age;
	
	private Integer score;
	
	private List<String> classmates;
	
	private List<String> courses = new ArrayList<String>();;

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public List<String> getClassmates() {
		return classmates;
	}

	public void setClassmates(List<String> classmates) {
		this.classmates = classmates;
	}

	public List<String> getCourses() {
		return courses;
	}

	public void setCourses(List<String> courses) {
		this.courses = courses;
	}
}
