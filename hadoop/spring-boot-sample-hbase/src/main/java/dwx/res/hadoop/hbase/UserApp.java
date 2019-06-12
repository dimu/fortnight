//package dwx.res.hadoop.hbase;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//@EnableAutoConfiguration
//@ComponentScan(basePackages = { "dwx.res.hadoop.hbase" })
//public class UserApp {
//
//	private static final Logger log = LoggerFactory.getLogger(UserApp.class);
//
//	public static void main(String[] args) throws Exception {
//		ConfigurableApplicationContext configurableApplicationContext = SpringApplication.run(
//				"classpath:/application-context.xml", args);
//
//		log.info("HBase Application Running");
//		
//		UserUtils userUtils = configurableApplicationContext.getBean(UserUtils.class);
//		userUtils.initialize();
//		userUtils.addUsers();
//	}
//}
