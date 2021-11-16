package dwx.tech.res.flink.stream;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

/**
 * flink的基本框架
 */
public class AnatomyOfFlink {

	public static void main(String[] args) throws Exception {
		final StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//		sEnv.readFile(new RowCsvInputFormat());
		URL url = AnatomyOfFlink.class.getClassLoader().getResource("data/student.csv");
		RowCsvInputFormat rowCsvInputFormat = new RowCsvInputFormat(new Path(url.toURI()),
				new TypeInformation[]{TypeInformation.of(String.class),TypeInformation.of(String.class),TypeInformation.of(Integer.class),TypeInformation.of(String.class)});
		DataStream<Row> ds =  sEnv.readFile(rowCsvInputFormat, url.getPath()).name("CSV Input Source");

//		ds.map(key-> {
//			System.out.println(key);
//			return key;
//		}).print();
		ds.print();

		System.out.println("over");
		sEnv.execute("anatomy of flink");
	}

}
