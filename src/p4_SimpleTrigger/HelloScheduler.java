package p4_SimpleTrigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * SimpleTrigger:
 * 在一个指定时间段内执行一次或者多次任务，甚至是一直执行下去，直到程序停止
 */

/**
 * 注意：
 * withRepeatCount()中的值可以是0或者其他正整数，REPEAT_INDEFINITELY表示永远
 * withIntervalInSeconds中的值必须是0或者正整数 - - 不然会报错：IllegalArgumentException: Repeat interval must be >= 0
 * 如果指定endtime的话，设置的withRepeatCount将会被覆盖
 */
public class HelloScheduler {
    public static void main(String[] args) {
        Date nowdate = new Date();
        String nowtime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(nowdate);
        System.out.println("nowtime:" + nowtime);
        Date startdate = new Date();
        //开始时间设置为3秒后
        startdate.setTime(nowdate.getTime() + 3000);
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();
        SimpleTrigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每两秒触发一次
                                .withIntervalInSeconds(-1)
                                //SimpleTrigger.REPEAT_INDEFINITELY：表示永远执行
                                .withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)
                )
                .startAt(startdate)
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
