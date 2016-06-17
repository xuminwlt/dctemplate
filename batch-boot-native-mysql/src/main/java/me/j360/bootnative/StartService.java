package me.j360.bootnative;

import org.junit.*;
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
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.mortbay.jetty.Server;
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



    private String outputFile = "./target/contacts.txt";

    @Autowired
    private Environment env;

    @Value("${test}")
    private String test;

    @PostConstruct
    public void init() throws Exception {

        System.out.println("---env##"+ env.getProperty("test"));
        System.out.println("---#"+ test);

    }

    public void getTest(){
        System.out.println("---#"+ test);
    }

    public void start() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        long start = System.currentTimeMillis();
        JobExecution execution = jobLauncher.run(itemEnrichmentJob,
                new JobParametersBuilder().addLong("time", System.currentTimeMillis()).toJobParameters());
        assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
        System.out.println("sync job executed in "+(System.currentTimeMillis()-start)+" ms");
        assertEquals(5,jdbcTemplate.queryForObject("select count(1) from contact",Integer.class).intValue());
        List<Map<String,Object>> contacts = jdbcTemplate.queryForList("select * from contact");
        for(Map<String,Object> item : contacts) {
            Assert.assertTrue(item.get("ssn").toString().length() == 9 + 2);
            Assert.assertTrue(item.get("ssn").toString().endsWith(item.get("id").toString()));
        }
    }

    private static final Server server = new Server(8085);


    @Autowired
    private Job itemEnrichmentJob;

    @Autowired
    private Job asyncItemEnrichmentJob;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        jdbcTemplate.update("delete from contact");
    }



    //1s延迟，1分钟轮训
    @Scheduled(fixedDelay = 1000)
    public void repeat(){
        System.out.println("repeat...");
    }
}
