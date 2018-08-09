package p1_Base;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

/**
 * jobdetail:
 * jobdetail为job实例提供了许多设置属性，以及jobdatamap成员变量属性
 * jobdatamap用来存储特定job实例的状态信息，调度器需要借助jobdetail对象来添加job实例
 * jobdetail靠JobBuilder来创建
 */
public class HelloScheduler {
    public static void main(String[] args) {
        //创建jobdetail实例
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                // .withIdentity("job1", "group1")
                .withIdentity("job1")
                .build();
        //jobdetail的名字
        System.out.println("jobdetail-name:" + jobDetail.getKey().getName());
        //jobdetail的分组 ---如果不设置的话，默认值为DEFAULT
        System.out.println("jobdetail-group:" + jobDetail.getKey().getGroup());
        //job类
        System.out.println("jobdetail-job:" + jobDetail.getJobClass().getName());
        //创建trigger实例
        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                //每隔两秒执行一次
                                .withIntervalInSeconds(2)
                                //直到永远
                                .repeatForever()
                )
                .build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        try {
            //创建scheduler实例
            Scheduler scheduler = schedulerFactory.getScheduler();
            scheduler.scheduleJob(jobDetail, trigger);
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
