package p1_Base;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * job实例在quartz中的生命周期：
 * job只有一个方法---execute
 * 每次调度器执行job时，他在调用execute方法前会创建一个新的job实例，
 * 当调用完成之后，关联的job对象实例会被释放，释放的实例会被垃圾回收机制回收
 */

/**
 * JobExecutionContext:
 * 当scheduler调用一个job，就会将JobExecutionContext传递给job的execute方法
 * job能通过JobExecutionContext对象访问到quartz运行时候的环境以及job本身的明细数据
 */
public class HelloJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String nowtime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        System.out.println("nowtime:" + nowtime);
        System.out.println("Hello World");
    }
}
