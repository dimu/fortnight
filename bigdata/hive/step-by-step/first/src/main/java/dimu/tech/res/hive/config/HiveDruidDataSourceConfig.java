package dimu.tech.res.hive.config;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.Data;
import org.apache.hive.jdbc.HiveDataSource;
import org.apache.hive.jdbc.HiveDriver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;

/**
 * hive 数据源连接配置，以及数据库连接池配置
 *
 * @author dwx
 * @date 2020-09-21
 */
@Configuration
@ConfigurationProperties(prefix = "spring.datasource.hive")
@Data
public class HiveDruidDataSourceConfig {

	private String url;
	private String username;
	private String password;
	private String driverClassName;
	private int initialSize;
	private int minIdle;
	private int maxWait;
	private int timeBetweenEvictionRunsMillis;
	private int minEvictableIdleTimeMillis;
	private String validationQuery;
	private boolean testWhileIdle;
	private boolean testOnBorrow;
	private boolean testOnReturn;
	private boolean poolPreparedStatements;
	private int maxPoolPreparedStatementPerConnectionSize;

//	@Bean(name = "hiveDruidDataSource")
//	public DataSource dataSource() {
//
//		HiveDataSource datasource = new HiveDataSource();
//
//		// datasource config
//		datasource.setUrl(url);
////		datasource.setUsername(username);
////		datasource.setPassword(password);
//		datasource.setDriverClassName(driverClassName);

		// pool configuration
//		datasource.setInitialSize(initialSize);
//		datasource.setMinIdle(minIdle);
//		datasource.setMaxWait(maxWait);
//		datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
//		datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
//		datasource.setValidationQuery(validationQuery);
//		datasource.setTestWhileIdle(testWhileIdle);
//		datasource.setTestOnBorrow(testOnBorrow);
//		datasource.setTestOnReturn(testOnReturn);
//		datasource.setPoolPreparedStatements(poolPreparedStatements);
//		datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);

//		return datasource;
//	}

	@Bean(name = "hiveDruidTemplate")
	public JdbcTemplate hiveDruidTemplate() {
		SimpleDriverDataSource driverDataSource = new SimpleDriverDataSource(new HiveDriver(), url, username, password);
		return new JdbcTemplate(driverDataSource);
	}
}
