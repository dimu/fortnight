package reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class Eat {
	public void print(String s) {
		System.out.println("eat: " + s);
	}
}

/**
 * reflect basic use 
 * @author dwx
 * 2016年12月28日
 *
 */
public class ReflectRes {

	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException, IllegalArgumentException, InvocationTargetException {
		
		Eat eat = new Eat();
		@SuppressWarnings("unchecked")
		Class<Eat> c1 = (Class<Eat>) eat.getClass();
//		Class<Eat> c2 = Eat.class;
		@SuppressWarnings("unchecked")
		Class<Eat> c3 = (Class<Eat>) Class.forName("reflect.Eat");
		System.out.println(c1.getClassLoader() + ":" + c1.getPackage());
		System.out.println(c3.getName() + ":" + c3.getSimpleName() + ":" + c3.getCanonicalName() );
		((Eat)(c3.newInstance())).print("c3");
		
		//获取方法
		for(Method em : Object.class.getDeclaredMethods()) {
			System.out.println("methodName:" + em.getName());
			System.out.println("returnType:" + em.getReturnType().getSimpleName());
			//modifiers 参考
			System.out.println("methodModifiers:" + Integer.toHexString(em.getModifiers()));
			for(Class<?> t : em.getParameterTypes()) {
//				System.out.println(t.getName());
				System.out.println("parameterType:" + t.getSimpleName());
			}
		}
		
		System.out.println("-------------------------------------");
		
		//获取成员变量
		for(Field item  : String.class.getDeclaredFields()) {
			System.out.println("fieldName:" + item.getName());
			System.out.println("fieldType:" + item.getType());
			System.out.println("fieldModifiers:" + item.getModifiers());
		}
		
		//获取构造方法
		System.out.println("-------------------------------------");
		for (Constructor<?> en : String.class.getConstructors()) {
			System.out.println("constructor name:" + en.getName());
			for(Class<?> x : en.getParameterTypes()) {
				System.out.println("constructor parameter:" + x.getName() );
			}
		}
		
		//方法反射，method调用
		System.out.println("-------------------------------------");
		Method method = c1.getMethod("print", String.class);
//		method.invoke(c1, "invoke");
		method.invoke(eat, "invoke");
		
	}

}
