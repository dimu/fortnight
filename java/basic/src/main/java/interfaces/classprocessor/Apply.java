package interfaces.classprocessor;

import java.util.*;
import static net.mindview.util.Print.*;

/**
 * 基类
 * @author dwx
 *
 */
class Processor {
	//定义公用的方法
	public String name() {
		return getClass().getSimpleName();
	}

	//暴露给子类的方法，一般来说定义为抽象方法
	Object process(Object input) {
		return input;
	}
}

/**
 * 转大些子类
 * @author dwx
 *
 */
class Upcase extends Processor {
	String process(Object input) { 
		// Covariant return
		return ((String) input).toUpperCase();
	}
}

/**
 * 转小写子类
 * @author dwx
 *
 */
class Downcase extends Processor {
	String process(Object input) {
		return ((String) input).toLowerCase();
	}
}

/**
 * 分割子类
 * @author dwx
 *
 */
class Splitter extends Processor {
	String process(Object input) {
		return Arrays.toString(((String) input).split(" "));
	}
}

class Nochange extends Processor {
}

public class Apply {
	
	//定义好处理流程与输入的参数，Processor具体的实现以及重写的process来决定业务逻辑
	public static void process(Processor p, Object s) {
		print("Using Processor " + p.name());
		print(p.process(s));
	}

	public static String s = "Disagreement with beliefs is by definition incorrect";

	public static void main(String[] args) {
		//转大写对象
		process(new Upcase(), s);
		//转小写对象
		process(new Downcase(), s);
		//分割对象
		process(new Splitter(), s);
		//什么也不做，采用默认的行为
		process(new Nochange(), s);
	}
} 
/*
 * Output: Using Processor Upcase DISAGREEMENT WITH BELIEFS IS BY DEFINITION
 * INCORRECT Using Processor Downcase disagreement with beliefs is by
 * definition incorrect Using Processor Splitter [Disagreement, with,
 * beliefs, is, by, definition, incorrect]
 */
