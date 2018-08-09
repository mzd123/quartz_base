package p2_JobDateMap;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * jobdatamap参数值的三种获取方式：
 * 1、jobdetail和trigger分开获取各自存储中的参数值
 * 2、总体获取jobdetail和trigger中的参数值
 * 3、通过get-set方式获取参数值 - - - Job中需要提供get和set方法，并且jobdetail和trigger中设置参数的名字要和job中属性名字保持一致，不然没法赋值
 */
public class HelloJob implements Job {

    private String name;
    private String age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String nowtime = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date());
        System.out.println("nowtime:" + nowtime);
        /**
         * jobdetail和trigger分开获取各自存储中的参数
         */
        //获取jobdetail中传入的参数集合:jobExecutionContext.getJobDetail().getJobDataMap()
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String ceshi = jobDataMap.getString("ceshi");
        //浮点类型
        float f = jobDataMap.getFloat("float");
        //获取trigger中传入的参数集合:jobExecutionContext.getTrigger().getJobDataMap()
        JobDataMap jobDataMap1 = jobExecutionContext.getTrigger().getJobDataMap();
        //如果trigger中没有加入相对应的key和value的话，获取的value将会是null
        String ceshi2 = jobDataMap1.getString("ceshi2");
        System.out.println(f);
        System.out.println(ceshi + "   " + ceshi2);
        /**
         * 总体获取jobdetail和trigger中的参数
         * jobExecutionContext.getMergedJobDataMap()
         * 注意：
         * jobdetail和trigger同时在usingJobData中存入key值一样，但是value值不一样的时候，trigger会会将jobdetail覆盖
         */
        JobDataMap jobDataMap2 = jobExecutionContext.getMergedJobDataMap();
        String ceshiz = jobDataMap2.getString("ceshi");
        //这里就体现了覆盖：jobdetail和trigger都有ceshi这个参数，但是这里获取的就是trigger中赋值的那个
        System.out.println(ceshiz);

        /**
         * get-set方法获取传入的值
         */
        System.out.println(name);
        System.out.println(age);
    }
}
