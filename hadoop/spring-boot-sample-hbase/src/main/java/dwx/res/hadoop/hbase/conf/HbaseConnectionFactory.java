package dwx.res.hadoop.hbase.conf;

import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.springframework.context.annotation.Bean;

public class HbaseConnectionFactory {
	
	public static final Configuration configuration = HBaseConfiguration.create();
	
	public static Connection con = null;
	
	/**
	 * get the hbase connection
	 * @return con pool
	 */
	public static Connection getConnection() {
		if (null == con) {
			configuration.set("hbase.zookeeper.quorum", "centos22,slave24,slave21,slave26");
			configuration.set("hbase.zookeeper.property.clientPort", "2181");
			
			try {
				con = ConnectionFactory.createConnection(configuration);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return con;
	}
	
	/**
	 * spring bean 注解生成 org.apache.hadoop.hbase.client.Connection对象
	 * @return 生成的connection对象
	 */
	@Bean
	public static Connection connection() {
		if (null == con) {
			configuration.set("hbase.zookeeper.quorum", "centos22");
			configuration.set("hbase.zookeeper.property.clientPort", "2181");
			
			try {
				con = ConnectionFactory.createConnection(configuration);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		return con;
	}
}
