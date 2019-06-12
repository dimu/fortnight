package dwx.res.hadoop.hbase.job;

import java.util.Calendar;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dwx.res.hadoop.hbase.mrunion.MRJob;


/**
 * 通过Quartz来触发mapreduce任务的运行
 * @author dwx
 * 2016年11月30日
 *
 */
public class MRQuartz implements Job{
	
	private static final Logger LOG  = LoggerFactory.getLogger(MRQuartz.class);
	
	// 该方法实现需要执行的任务
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		MRJob mrJob = new MRJob();
		try {
			//任务实际调度时间
			String[] args = getCollectInterval(jobExecutionContext);
			mrJob.run(args);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
	}
	
	/**
	 * 根据job的实际开始时间来
	 * @param jobExecutionContext quartz的执行上下文
	 * @return 返回采集时间段，第一个为区间的开始时间，第二个为截止时间
	 */
	public synchronized static String[] getCollectInterval(JobExecutionContext jobExecutionContext) {
		Date fireDate = jobExecutionContext.getFireTime();
		Calendar cal = Calendar.getInstance();
		cal.setTime(fireDate);
		//由于实际出发的时间可能不是在0秒0微秒，需要将该字段设置为0
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		
		String endDate = cal.getTime().toString();
		String endTime = String.valueOf(cal.getTimeInMillis());
		cal.add(Calendar.MINUTE, -15);
		String startDate = cal.getTime().toString();
		String startTime = String.valueOf(cal.getTimeInMillis());
		
		LOG.info("任务采集去加为：{}-{}", startDate, endDate);
		
		return new String[]{startTime, endTime};
	}

	public static void main(String[] args) {
		
		try {
			// 创建Scheduler
			SchedulerFactory schedulerFactory = new org.quartz.impl.StdSchedulerFactory();
			Scheduler sched = schedulerFactory.getScheduler();
			sched.start();
			
			// 创建JobDetail，指明name，groupname，以及具体的Job类名，该Job负责定义需要执行任务
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("type", "test");
			JobDetail jobDetail = JobBuilder.newJob(MRQuartz.class).withIdentity("myJob").withDescription("hello").usingJobData(jobDataMap).build();
		
			// 创建Trigger，每隔15分钟执行
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("myTrigger", "myTriggerGroup"))
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInMinutes(15).repeatForever())
					.startAt(new Date()).build();
			
			// 用scheduler将JobDetail与Trigger关联在一起，开始调度任务
			sched.scheduleJob(jobDetail, trigger);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
