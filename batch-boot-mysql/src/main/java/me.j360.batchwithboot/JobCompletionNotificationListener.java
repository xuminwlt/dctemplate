package me.j360.batchwithboot;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:45
 * 说明：
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {

    }

    @Override
    public void beforeJob(JobExecution jobExecution) {
        super.beforeJob(jobExecution);
    }
}
