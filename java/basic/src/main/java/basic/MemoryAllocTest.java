package basic;

import java.lang.reflect.Constructor;

public class MemoryAllocTest {
	
    private static String str1;
    private static char c;
    
    static{
    	System.out.println(str1);
    	System.out.println(c);
    	str1 = "abc";
    }
    
 
    private double str2 = Math.random();
    private int int3 = 3;
    
    public MemoryAllocTest() {
    	System.out.println(str2);
    }
    
    public static void main(String[] args) throws ClassNotFoundException {
    	System.out.println("test!");
    	
    	MemoryAllocTest test = new MemoryAllocTest();
    	Class<?> clz = Class.forName("basic.MemoryAllocTest");
    	Constructor<?>[] constructor = clz.getConstructors();
    	for (Constructor<?> em : constructor) {
    		System.out.println(em.getName());
    	}
    	System.out.println(clz.getName());
    	System.out.println(clz.getSimpleName());
    	
 
    }
    
}
