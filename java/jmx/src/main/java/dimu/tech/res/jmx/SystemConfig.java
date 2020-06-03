package dimu.tech.res.jmx;

/**
 * @author dwx
 * @date 2020-06-03
 */
public class SystemConfig implements SystemConfigMXBean {

	private int threadCount;

	private String schemaName;

	public SystemConfig(int numThreads, String schema){
		this.threadCount=numThreads;
		this.schemaName=schema;
	}

	public void setThreadCount(int noOfThreads) {
		this.threadCount=noOfThreads;
	}

	public int getThreadCount() {
		return this.threadCount;
	}

	public void setSchemaName(String schemaName) {
		this.schemaName=schemaName;
	}

	public String getSchemaName() {
		return this.schemaName;
	}

	public String doConfig(){
		return "No of Threads="+this.threadCount+" and DB Schema Name="+this.schemaName;
	}
}
