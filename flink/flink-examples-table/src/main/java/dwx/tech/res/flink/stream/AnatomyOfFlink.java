package dwx.tech.res.flink.stream;

import java.io.IOException;

import org.apache.flink.api.common.io.FileInputFormat;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.io.RowCsvInputFormat;
import org.apache.flink.core.fs.Path;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.types.Row;

public class AnatomyOfFlink {

	public static void main(String[] args) {
		final StreamExecutionEnvironment sEnv = StreamExecutionEnvironment.getExecutionEnvironment();
//		sEnv.readFile(new RowCsvInputFormat())
		DataStreamSource<Row> ds =  sEnv.readFile(new RowCsvInputFormat(new Path("file:///D:/student.csv"), new TypeInformation[]{}),"file:///D:/student.csv");

		ds.map(key-> {
			System.out.println(key);
			return key.split(",");
		});

		System.out.println("over");
	}

}
