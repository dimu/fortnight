package java8.functionalinterface;

/**
 * 函数式接口，只能够有唯一的抽象方法，function descriptor为
 * 抽象方法的signature,java api定义了很多内置的函数式接口，例如Comparable，
 * Runnable and Callable等，采用函数式编程首先需要抽象出函数描述符，
 * 此方法与java api 定义的Predicate一样
 * @author dwx
 *
 */
@FunctionalInterface
public interface ITrade {
	boolean check(Trade T);
}
