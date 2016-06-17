package me.j360.bootnative.jdbc;

import org.apache.commons.io.FileUtils;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Package: me.j360.bootnative.jdbc
 * User: min_xu
 * Date: 16/6/17 下午3:01
 * 说明：
 */

@Service
public class StartService {



    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private String outputFile = "./target/contacts.txt";

    @Autowired
    private Environment env;

    @Value("${test}")
    private String test;

    @PostConstruct
    public void init(){

        System.out.println("---env##"+ env.getProperty("test"));
        System.out.println("---#"+ test);

    }

    public void getTest(){
        System.out.println("---#"+ test);
    }

    public void start(){
        try {
            JobExecution execution = jobLauncher.run(job, new JobParametersBuilder()
                            .addString("output.file", "file:" + outputFile)
                            .toJobParameters()
            );
            assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
            File file = new File(outputFile);
            assertTrue(file.exists());
            try {
                assertEquals(
                        jdbcTemplate.queryForObject("select count(1) from contact",Integer.class).intValue(),
                        FileUtils.readLines(file).size()
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (JobExecutionAlreadyRunningException e) {
            e.printStackTrace();
        } catch (JobRestartException e) {
            e.printStackTrace();
        } catch (JobInstanceAlreadyCompleteException e) {
            e.printStackTrace();
        } catch (JobParametersInvalidException e) {
            e.printStackTrace();
        }

    }



    //1s延迟，1分钟轮训
    @Scheduled(fixedDelay = 1000)
    public void repeat(){
        System.out.println("repeat...");
    }
}
