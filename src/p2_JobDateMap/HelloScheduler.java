package p2_JobDateMap;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * 在进行任务调用时，jobdatamap存储在JobExecutionContext中
 * jobdatamap可以用来装载任何可序列化的数据对象，当job实例对象执行时，这些参数对象会传递给它
 * jobdatamap实现了jdk中的map接口，并且添加了一些非常方便的方法用来存取基本数据类型
 */

public class HelloScheduler {
    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                /**
                 * 可传入的值类型有：Integer,Float,Double,Boolean,Stirng
                 */
                //在jobdetail中传入参数值---String
                .usingJobData("ceshi", "aa")
                //在jobdetail中传入参数值---float
                .usingJobData("float", 3.14F)
                .usingJobData("name", "小明")
                .usingJobData("age", "20")
                .build();
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInSeconds(2).repeatForever()
                )
                /**
                 * 可传入的值类型有：Integer,Float,Double,Boolean,Stirng
                 */
                //在trigger中传入参数值
                .usingJobData("ceshi", "bb")
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
