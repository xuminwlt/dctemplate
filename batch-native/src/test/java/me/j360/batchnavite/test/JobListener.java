package me.j360.batchnavite.test;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * Package: me.j360.batchnavite.test
 * User: min_xu
 * Date: 16/6/15 下午8:59
 * 说明：
 */
public class JobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {

    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        if( jobExecution.getStatus() == BatchStatus.COMPLETED ){
            //job success
        }
        else if(jobExecution.getStatus() == BatchStatus.FAILED){
            //job failure
        }
    }
}
