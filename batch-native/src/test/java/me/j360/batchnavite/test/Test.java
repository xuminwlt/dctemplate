package me.j360.batchnavite.test;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * Package: me.j360.batchnavite.test
 * User: min_xu
 * Date: 16/6/15 下午8:56
 * 说明：这段代码等同于batch2
 */
public class Test {

    public void test(){
        Job job = new SimpleJob();
        job.setRestartable(false);

        JobParameters jobParameters = new JobParameters();

        JobExecution firstExecution = jobRepository.createJobExecution(job, jobParameters);
        jobRepository.saveOrUpdate(firstExecution);

        try {
            jobRepository.createJobExecution(job, jobParameters);
            fail();
        }
        catch (JobRestartException e) {
            // expected
        }
    }



}
