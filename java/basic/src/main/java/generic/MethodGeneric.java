package generic;

//泛型方法，能够使用泛型方法解决问题的地方就不要用泛型类
public class MethodGeneric {
	//第一个参数
	public <T> void f(T x){
		System.out.println(x.getClass().getName());
	}
	
	//接受三个不同的参数
	public <T, V, X> void f(T x, V y, X z){
		System.out.println(x.getClass().getName() + y.getClass().getName() + z.getClass().getName());
	}
	
	public static void main(String[] args){
		MethodGeneric mg = new MethodGeneric();
		mg.f("");
		mg.f(1);
		mg.f(1.0);
		mg.f('x');
		mg.f("123");
		mg.f(true);
		mg.f(new MethodGeneric());
		mg.f("", 1, 1.0);
	}
}
