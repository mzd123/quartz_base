package p5_CronTrigger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Trigger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HelloJob implements Job {


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //获取trigger
        Trigger trigger = jobExecutionContext.getTrigger();
        System.out.println("nowtime" + new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date()));
        System.out.println("Hello World");
    }
}
