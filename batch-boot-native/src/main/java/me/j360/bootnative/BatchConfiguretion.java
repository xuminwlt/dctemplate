package me.j360.bootnative;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.support.ApplicationContextFactory;
import org.springframework.batch.core.configuration.support.GenericApplicationContextFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Package: me.j360.bootnative
 * User: min_xu
 * Date: 16/6/16 下午8:52
 * 说明：
 *
 *
 * If a user does not provide a DataSource within the context, a Map based JobRepository will be used. Note that only one of your configuration classes needs to have the @EnableBatchProcessing annotation. Once you have an @EnableBatchProcessing class in your configuration you will have an instance of StepScope and JobScope so your beans inside steps can have @Scope("step") and @Scope("job") respectively. You will also be able to @Autowired some useful stuff into your context:
 a JobRepository (bean name "jobRepository")
 a JobLauncher (bean name "jobLauncher")
 a JobRegistry (bean name "jobRegistry")
 a PlatformTransactionManager (bean name "transactionManager")
 a JobBuilderFactory (bean name "jobBuilders") as a convenience to prevent you from having to inject the job repository into every job, as in the examples above
 a StepBuilderFactory (bean name "stepBuilders") as a convenience to prevent you from having to inject the job repository and transaction manager into every step
 If the configuration is specified as modular=true then the context will also contain an AutomaticJobRegistrar. The job registrar is useful for modularizing your configuration if there are multiple jobs. It works by creating separate child application contexts containing job configurations and registering those jobs. The jobs can then create steps and other dependent components without needing to worry about bean definition name clashes. Beans of type ApplicationContextFactory will be registered automatically with the job registrar. Example:
 @Configuration
 @EnableBatchProcessing(modular=true)
 public class AppConfig {

 @Bean
 public ApplicationContextFactory someJobs() {
 return new GenericApplicationContextFactory(SomeJobConfiguration.class);
 }

 @Bean
 public ApplicationContextFactory moreJobs() {
 return new GenericApplicationContextFactory(MoreJobConfiguration.class);
 }

 ...

 }

 Note that a modular parent context in general should not itself contain @Bean definitions for job, especially if a BatchConfigurer is provided, because cyclic configuration dependencies are otherwise likely to develop.
 For reference, the first example above can be compared to the following Spring XML configuration:

 <batch>
 <job-repository />
 <job id="myJob">
 <step id="step1" .../>
 <step id="step2" .../>
 </job>
 <beans:bean id="transactionManager" .../>
 <beans:bean id="jobLauncher" class="org.springframework.batch.core.launch.support.SimpleJobLauncher">
 <beans:property name="jobRepository" ref="jobRepository" />
 </beans:bean>
 </batch>


 @EnableBatchConfiguration
 The @EnableBatchProcessing works similarly to the other @Enable* annotations in the Spring family. In this case, @EnableBatchProcessing provides a base configuration for building batch jobs. Within this base configuration, an instance of StepScope is created in addition to a number of beans made available to be autowired:

 JobRepository - bean name "jobRepository"
 JobLauncher - bean name "jobLauncher"
 JobRegistry - bean name "jobRegistry"
 PlatformTransactionManager - bean name "transactionManager"
 JobBuilderFactory - bean name "jobBuilders"
 StepBuilderFactory - bean name "stepBuilders"
 The core interface for this configuration is the BatchConfigurer. The default implementation provides the beans mentioned above and requires a DataSource as a bean within the context to be provided. This data source will be used by the JobRepository.

 */


@Configuration
@EnableBatchProcessing
public class BatchConfiguretion {


    @Autowired
    private JobRepository jobRepository;
    @Autowired
    private JobLauncher jobLauncher;
    @Autowired
    private JobRegistry jobRegistry;
    @Autowired
    private PlatformTransactionManager transactionManager;
    @Autowired
    private JobBuilderFactory jobBuilders;
    @Autowired
    private StepBuilderFactory stepBuilders;


    @Autowired
    public DataSource dataSource;

    @Autowired
    private Environment env;

    @Value("${test}")
    private String test;







}
