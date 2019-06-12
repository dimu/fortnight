package annotations;

import java.lang.annotation.*;

/**
 * description: 自定义注解，主要
 *
 * @version 2016年8月1日 上午11:13:42
 * @see modify content------------author------------date
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UseCase {
	public int id();

	public String description() default "no description";
}
