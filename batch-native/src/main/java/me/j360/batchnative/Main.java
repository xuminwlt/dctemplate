package me.j360.batchnative;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Package: me.j360.batchnative
 * User: min_xu
 * Date: 16/6/13 下午4:01
 * 说明：
 */
public class Main {
    /**
     * @param args
     */
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "batch.xml");
        JobLauncher launcher = (JobLauncher) context.getBean("jobLauncher");
        Job job = (Job) context.getBean("helloWorldJob");

        try {
            /* 运行Job */
            JobExecution result = launcher.run(job, new JobParameters());
            /* 处理结束，控制台打印处理结果 */
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
