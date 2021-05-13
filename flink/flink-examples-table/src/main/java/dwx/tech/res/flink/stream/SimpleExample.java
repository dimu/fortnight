package dwx.tech.res.flink.stream;

import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;

/**
 * flink official example
 *
 * @author dwx
 * @date 2020-08-13
 */
public class SimpleExample {

	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment env =
				StreamExecutionEnvironment.getExecutionEnvironment();

		DataStream<Person> flintstones = env.fromElements(
				new Person("Fred", 35),
				new Person("Wilma", 40),
				new Person("Pebbles", 2));

		DataStream<Person> adults = flintstones.filter(new FilterFunction<Person>() {
			@Override
			public boolean filter(Person person) throws Exception {
				return person.age >= 18;
			}
		});

		/**
		 * indicate which sub-task produced the output.
		 */
		adults.print();

		env.execute("simple example job");
	}

	public static class Person {
		public String name;
		public Integer age;
		public Person() {};

		public Person(String name, Integer age) {
			this.name = name;
			this.age = age;
		};

		@Override
		public String toString() {
			return this.name.toString() + ": age " + this.age.toString();
		};
	}

}
