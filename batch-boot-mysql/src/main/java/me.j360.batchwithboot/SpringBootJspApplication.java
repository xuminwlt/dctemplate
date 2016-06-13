package me.j360.batchwithboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;

/**
 * Package: me.j360.batchwithboot
 * User: min_xu
 * Date: 16/6/13 下午3:45
 * 说明：
 */
@ComponentScan("me.j360")
@SpringBootApplication
public class SpringBootJspApplication extends SpringBootServletInitializer {

    /**
     * 500一批
     * oracle : 单条插入基本每分钟2.5W条（50W，19.5min） ,批量插入基本每分钟10W条(50W，4.7mim)
     * mysql  : 单条插入基本每分钟2.5W条（50W，11.4min） ,批量插入基本每分钟40W条(50W，1.3min)
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SpringBootJspApplication.class);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(SpringBootJspApplication.class, new String[]{"appStart=true"});
    }
}
