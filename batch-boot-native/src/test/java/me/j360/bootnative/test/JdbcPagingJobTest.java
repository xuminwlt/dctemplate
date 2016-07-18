/**
 * 
 */
package me.j360.bootnative.test;

import org.apache.commons.io.FileUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author acogoluegnes
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/jdbc/jdbc-paging-job.xml")
public class JdbcPagingJobTest {

	Logger log = LoggerFactory.getLogger(JdbcPagingJobTest.class);

	@Autowired
	private Job job;
	
	@Autowired
	private JobLauncher jobLauncher;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	private String outputFile = "./target/contacts.txt";
	
	@Test public void jdbcPaging() throws Exception {
		JobExecution execution = jobLauncher.run(job, new JobParametersBuilder()
			.addString("output.file", "file:"+outputFile)
			.toJobParameters()
		);
		assertEquals(ExitStatus.COMPLETED, execution.getExitStatus());
		File file = new File(outputFile);
		assertTrue(file.exists());
		assertEquals(
			jdbcTemplate.queryForObject("select count(1) from contact",Integer.class).intValue(), 
			FileUtils.readLines(file).size()
		);


	}


	/**
	 * 测试自定义的logback输出日志格式打印
	 * 1.定义DiyEncoder
	 * 2.定义DiyLayout
	 * 3.定义DiyThrowableProxyCoder
	 * 4.配置DiyThrowableProxyCoder到DiyLayout
	 * 5.配置logback.xml pattern -> %d{HH:mm:ss.SSS} [%thread] %-5level %logger{36} - %msg%ex%n   ex表示如果有异常时的输出格式
	 * 6.test
	 *
	 */
	@Test
	public void test(){
		try {
			String sss = null;
			//sss.equals("1");
			int i = 1/0;
		}catch (Exception e){
			log.error(e.getMessage(),e);
		}
	}
}
