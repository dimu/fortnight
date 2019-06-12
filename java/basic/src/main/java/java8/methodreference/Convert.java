package java8.methodreference;

@FunctionalInterface
public interface Convert<T, U> {
	U convert(T from);
}
