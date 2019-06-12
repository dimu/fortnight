package job;

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

/**
 * Quartz 设计的核心类包括 Scheduler, Job 以及 Trigger。
 * 其中，Job 负责定义需要执行的任务，Trigger 负责设置调度策略，
 * Scheduler 将二者组装在一起，并触发任务开始执行
 * 
 * @author dwx
 * 2016年11月29日
 *
 */
public class QuartzTest implements Job {

	// 该方法实现需要执行的任务
	@Override
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
	}

	public static void main(String[] args) {
		try {
			// 创建一个Scheduler
			SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
			Scheduler sched = schedFact.getScheduler();
			sched.start();
			// 创建一个JobDetail，指明name，groupname，以及具体的Job类名，
			// 该Job负责定义需要执行任务
			// JobDetail jobDetail = new JobDetail("myJob", "myJobGroup",
			// QuartzTest.class);
			JobDataMap jobDataMap = new JobDataMap();
			jobDataMap.put("type", "test");
			JobDetail jobDetail = JobBuilder.newJob(QuartzTest.class).withIdentity("myJob").withDescription("hello").usingJobData(jobDataMap).build();
			// jobDetail.getJobDataMap().put("type", "FULL");
			// 创建一个每周触发的Trigger，指明星期几几点几分执行
			// Trigger trigger = TriggerUtils.makeWeeklyTrigger(3, 16, 38);
			Trigger trigger = TriggerBuilder.newTrigger().withIdentity(new TriggerKey("myTrigger", "myTriggerGroup"))
					.withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(1).repeatForever())
					.startAt(new Date()).build();
			// trigger.setGroup("myTriggerGroup");
			// 从当前时间的下一秒开始执行
			// trigger.setStartTime(TriggerUtils.getEvenSecondDate(new Date()));
			// 指明trigger的name
			// trigger.setName("myTrigger");
			// 用scheduler将JobDetail与Trigger关联在一起，开始调度任务
			sched.scheduleJob(jobDetail, trigger);
//			sched.rescheduleJob(triggerKey, newTrigger)
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
