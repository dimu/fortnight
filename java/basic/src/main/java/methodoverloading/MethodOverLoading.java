package methodoverloading;

/**
 * 重载方法名相同，区别是唯一的参数列表不相同，返回值不能作为重载的
 * 判定依据，因为进行方法调用的时候，有可能只是调用方法而忽略掉返回值
 * 所以根据返回值调用不靠谱，但是移动不同参数类型的顺序会判定为重载，
 * 但是这样不利于维护
 */
public class MethodOverLoading {
	
	public void cal(int a, int b) {
		
	}
	
	//返回值不能作为重载的判断依据
//	public int cal(int a, int b) {
//		return 0;
//	}
	
	//异常不能作为重载的判断依据
//	public void cal(int a, int b) throws Exception {
//		return 0;
//	}
	
	//修饰符不能作为重载的依据
//	protected void cal(int a, int b) {
//		
//	}
	
	public void cal(int a, String b) {
		
	}
	
	//虽然参数的类型一样但是，但是由于每个方法保存的唯一的参数列表的顺序不一样，也认为是overloading
	public void cal(String b, int a) {
		
	}
}
