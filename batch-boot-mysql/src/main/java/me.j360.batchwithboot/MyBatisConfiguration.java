package me.j360.batchwithboot;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:46
 * 说明：
 */
@Configuration
public class MyBatisConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfiguration.class);

    @Autowired
    SqlSessionFactory sqlSessionFactory;
    @Autowired
    SqlSessionTemplate sessionTemplate;

    @Bean
    public SqlSessionTemplate sqlSessionTemplate() {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public BlackListDao blackListMapper() {
        return sessionTemplate.getMapper(BlackListDao.class);
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        logger.debug("初始化dataSource");
        return new DruidDataSource();
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) {
        PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
        return transactionManager;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {

        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        // 获取数据库类型
        String dataBaseType = ProperitesUtil.getPropertyValue("spring.boot.database") == null ? "mysql"
                : ProperitesUtil.getPropertyValue("spring.boot.database");

        String directory = "classpath:/mapper/" + dataBaseType.trim().toLowerCase() + "/*.xml";
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources(directory));

        return sqlSessionFactoryBean.getObject();
    }

}
