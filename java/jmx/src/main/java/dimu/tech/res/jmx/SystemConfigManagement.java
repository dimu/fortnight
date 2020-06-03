package dimu.tech.res.jmx;

import java.lang.management.ManagementFactory;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

/**
 * Java Management extensions demo
 * @author dwx
 * @date 2020-06-03
 */
public class SystemConfigManagement {

	private static final int DEFAULT_NO_THREADS=10;

	private static final String DEFAULT_SCHEMA="default";

	public static void main(String[] args) throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InterruptedException {
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
		//register the MBean
		SystemConfig mBean = new SystemConfig(DEFAULT_NO_THREADS, DEFAULT_SCHEMA);
		ObjectName name = new ObjectName("dimu.tech.res.jmx:type=SystemConfigMBean");
		mbs.registerMBean(mBean, name);
		do{
			Thread.sleep(3000);
			System.out.println("Thread Count="+mBean.getThreadCount()+":::Schema Name="+mBean.getSchemaName());
		}while(mBean.getThreadCount() !=0);

	}
}
