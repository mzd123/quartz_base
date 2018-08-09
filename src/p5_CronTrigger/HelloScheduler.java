package p5_CronTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * CronTrigger：
 * 基于日历的任务调度器
 * 不像SimpleTrigger那样精确指定时间间隔
 * <p>
 * 由7个子表达式组成的字符串，描述了时间表的详细信息
 * 格式：秒、分、小时、日、月、周、年
 */
public class HelloScheduler {
    public static void main(String[] args) {
        Date nowdate = new Date();
        String nowtime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(nowdate);
        System.out.println("nowtime:" + nowtime);
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();
        CronTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* 18 21 * * ?")
                )
                .build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
