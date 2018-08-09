package p6_Scheduler;

import org.quartz.*;
import org.quartz.impl.DirectSchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Scheduler：
 * 两种创建方式：
 * 1、用的较多
 * SchedulerFactory schedulerFactory = new StdSchedulerFactory();
 * Scheduler scheduler = schedulerFactory.getScheduler();
 * 2、
 * DirectSchedulerFactory schedulerFactory1 = DirectSchedulerFactory.getInstance();
 * Scheduler scheduler1=schedulerFactory1.getScheduler();
 */

/**
 * StdSchedulerFactory：
 * 使用一组参数（properties）来创建和初始化quartz调度器
 * 配置参数一般存储字啊quartz.properties中
 * 调用getScheduler方法就能穿件和初始化调度器对象
 * <p>
 * 几个主要函数：
 * 1、scheduleJob（jobDetail，trigger）
 * 这个函数返回值是Date类型，主要作用就是绑定jobdetail和trigger，
 * 然后将他注册进入scheduler中，那么scheduler将会根据trigger去完成jobdetail任务
 * 返回值：表示的这个任务下一次要触发的时间
 * <p>
 * 2、start（）
 * 这个很简单，就是启动我们的scheduler
 * <p>
 * 3、standby()
 * 表示暂时挂起，是可以被重新开启的
 * 再次调用start()方法的话，这个scheduler将会被重新启用
 * <p>
 * 4、shutdown()
 * 关闭scheduler
 * shutdown里面可以增加一个boolean类型的参数
 * 如果设置为true的话，scheduler将会等待所有任务执行完毕才关闭
 * 如果设置为false或者没有设置的话，scheduler将会直接关闭，但是期间的任务还是会在scheduler关闭之后继续执行
 * <p>
 * 5、isShutdown()
 * 判断scheduler是否已经被关闭
 */
public class HelloScheduler {
    public static void main(String[] args) {
        JobDetail jobDetail = JobBuilder.newJob(HelloJob.class)
                .withIdentity("job1", "group1")
                .build();
        CronTrigger trigger = (CronTrigger) TriggerBuilder.newTrigger()
                .withIdentity("trigger1", "group1")
                .withSchedule(
                        CronScheduleBuilder.cronSchedule("* * 22 * * ?")
                )
                .build();
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        //DirectSchedulerFactory schedulerFactory1 = DirectSchedulerFactory.getInstance();
        try {
            Scheduler scheduler = schedulerFactory.getScheduler();
            // Scheduler scheduler1=schedulerFactory1.getScheduler();
            //下一次执行的时间
            System.out.println(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(scheduler.scheduleJob(jobDetail, trigger)));
            scheduler.start();
            try {
                /**
                 * standby
                 */
                //执行2秒后挂起
                //  Thread.sleep(2000);
                //scheduler.standby();
                //挂起3秒之后重启启动scheduler
                // Thread.sleep(3000);
                // scheduler.start();
                /**
                 * shutdown
                 */
                Thread.sleep(2000);
                scheduler.shutdown(true);
                //在重启就报错：org.quartz.SchedulerException: The Scheduler cannot be restarted after shutdown() has been called.
                //scheduler.start();
                System.out.println(scheduler.isShutdown());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }
}
