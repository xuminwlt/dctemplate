package me.j360.batchnative.boot;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.configuration.annotation.DefaultBatchConfigurer;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Package: me.j360.batchnative.boot
 * User: min_xu
 * Date: 16/6/16 下午3:28
 * 说明：
 *
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

 */

@Configuration
@EnableBatchProcessing
public class AppConfig extends DefaultBatchConfigurer {

    @Bean
    public Job job() {
        return null;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        return new MapJobRepositoryFactoryBean().getObject();
    }



}
