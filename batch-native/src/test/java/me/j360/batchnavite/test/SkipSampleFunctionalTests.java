package me.j360.batchnavite.test;

import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.batch.core.JobExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;

/**
 * Package: me.j360.batchnavite.test
 * User: min_xu
 * Date: 16/6/16 下午2:16
 * 说明：
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/simple-job-launcher-context.xml",
        "/jobs/skipSampleJob.xml" })
public class SkipSampleFunctionalTests {

    @Autowired
    private JobLauncherTestUtils jobLauncherTestUtils;

    private SimpleJdbcTemplate simpleJdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.simpleJdbcTemplate = new SimpleJdbcTemplate(dataSource);
    }

    @Test
    public void testJob() throws Exception {
        simpleJdbcTemplate.update("delete from CUSTOMER");
        for (int i = 1; i <= 10; i++) {
            simpleJdbcTemplate.update("insert into CUSTOMER values (?, 0, ?, 100000)",
                    i, "customer" + i);
        }

        JobExecution jobExecution = jobLauncherTestUtils.launchJob().getStatus();


        Assert.assertEquals("COMPLETED", jobExecution.getExitStatus());
    }



    @Test
    public void individual(){
        JobExecution jobExecution = jobLauncherTestUtils.launchStep("loadFileStep");
    }


}