package dimu.tech.res.jmx;

/**
 * @author dwx
 * @date 2020-06-03
 */
public interface SystemConfigMXBean {

	 void setThreadCount(int noOfThreads);

	 int getThreadCount();

	 void setSchemaName(String schemaName);

	 String getSchemaName();

	 String doConfig();
}
