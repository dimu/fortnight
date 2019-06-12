package timer;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * description: 编写简单的timer类，实现简单的任务
 *
 * @version 2016年8月31日 下午1:49:29
 * @see
 * modify content------------author------------date
 */
public class SimpleTimerTask extends TimerTask {
	
	public static int count;

	@Override
	public void run() {
		System.out.println(count++);
	}
	
	public static void main(String[] args) {
		SimpleTimerTask stt = new SimpleTimerTask();
		Timer timer = new Timer();
		timer.schedule(stt, new Date(), 5000);
	}
}
