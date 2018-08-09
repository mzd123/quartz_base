package p3_Trigger;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * trigger:
 * trigger对象是用来触发执行job的，即quartz中的触发器，用来告诉调度程序什么时候触发
 * <p>
 * trigger是靠TriggerBuilder创建
 * <p>
 * trigger通用属性：jobkey、starttime、endtime
 */

/**
 * jobkey:
 * 表示job实例的标识，触发器被触发时，该指定的job实例会执行
 */

/**
 * starttime:
 * 表示触发器首次被触发的时间，它的值的类型为Java.util.Date
 */

/**
 * endtime:
 * 指定触发器的不再被触发的时间，它的值的类型为Java.util.Date
 */

public class HelloScheduler {
    public static void main(String[] args) {
        Date nowdate=new Date();
        String nowtime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(nowdate);
        System.out.println("nowtime:" + nowtime);
        Date startdate =new Date();
        Date enddate=new Date();
        //开始时间设置为3秒后
        startdate.setTime(nowdate.getTime()+3000);
        //结束时间设置为6秒后
        enddate.setTime(nowdate.getTime()+6000);
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每两秒触发一次
                                .withIntervalInSeconds(2)
                                //直到永远- - -但是这里规定了结束时间，所以repeatForever将不起作用
                                .repeatForever()
                )
                .startAt(startdate)
                .endAt(enddate)
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
