package me.j360.batchwithboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.transaction.PlatformTransactionManager;

import java.io.IOException;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:43
 * 说明：
 */
@Configuration
@EnableBatchProcessing
public class BlackListBatchConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BlackListBatchConfiguration.class);
    /**
     * 读取外部文件方法
     * @return
     * @throws IOException
     */
    @Bean
    @StepScope
    public ItemReader<BlackListDO> reader(@Value("#{jobParameters[inputFileBlack]}") String inputFile) throws IOException {
        logger.info("inputFile:"+new ClassPathResource(inputFile).getURL().getPath());
        if(inputFile == null){
            logger.error("The blacklist reader file is null");
            return null;
        }
        FlatFileItemReader<BlackListDO> reader = new FlatFileItemReader<BlackListDO>();

        reader.setResource(new ClassPathResource(inputFile));

        reader.setLineMapper(lineMapper());

        reader.setLinesToSkip(1);

        reader.open(JobCompletionNotificationListener.jobExecution.getExecutionContext());

        return reader;
    }


    /**
     * 读取文本行映射POJO
     * @return
     */
    @Bean
    @StepScope
    public LineMapper<BlackListDO> lineMapper() {
        DefaultLineMapper<BlackListDO> lineMapper = new DefaultLineMapper<BlackListDO>();
        DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();
        lineTokenizer.setDelimiter(",");
        lineTokenizer.setStrict(false);
        lineTokenizer.setNames(new String[] { "type","value","fraudType"});

        BeanWrapperFieldSetMapper<BlackListDO> fieldSetMapper = new BeanWrapperFieldSetMapper<BlackListDO>();
        fieldSetMapper.setTargetType(BlackListDO.class);
        lineMapper.setLineTokenizer(lineTokenizer);
        lineMapper.setFieldSetMapper(new BlackListFieldSetMapper());
        return lineMapper;
    }

    /**
     * 处理过程
     * @return
     */
    @Bean
    @StepScope
    public ItemProcessor<BlackListDO, BlackListDO> processor(@Value("#{jobParameters[inputFileBlack]}") String inputFile) {
        return new BlackListDOItemProcessor(inputFile);
    }

    /**
     * 写出内容
     * @return
     */
    @Bean
    @StepScope
    public ItemWriter<BlackListDO> writer() {
        return new BlackListItemWriter();
    }

    /**
     * 构建job
     * @param jobs
     * @param s1
     * @param listener
     * @return
     */
    @Bean
    public Job importFileJob(JobBuilderFactory jobs, Step step1,JobExecutionListener listener,JobRepository jobRepository) {
        return jobs.get("importFileJob")
                .incrementer(new RunIdIncrementer())
                .repository(jobRepository)
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }

    /**
     * 声明step
     * @param stepBuilderFactory
     * @param reader
     * @param writer
     * @param processor
     * @return
     */
    @Bean
    public Step step1(StepBuilderFactory stepBuilderFactory, ItemReader<BlackListDO> reader,
                      ItemWriter<BlackListDO> writer, ItemProcessor<BlackListDO, BlackListDO> processor,PlatformTransactionManager transactionManager) {
        logger.error("step1");
        return stepBuilderFactory.get("step1")
                .<BlackListDO, BlackListDO> chunk(500)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .faultTolerant()
                .retry(Exception.class)   // 重试
                .noRetry(ParseException.class)
                .retryLimit(1)           //每条记录重试一次
                .listener(new RetryFailuireItemListener())
                .skip(Exception.class)
                .skipLimit(500)         //一共允许跳过200次异常
                .taskExecutor(new SimpleAsyncTaskExecutor()) //设置并发方式执行
                .throttleLimit(10)        //并发任务数为 10,默认为4
                .transactionManager(transactionManager)
                .build();
    }
}
