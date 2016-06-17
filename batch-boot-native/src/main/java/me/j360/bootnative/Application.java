package me.j360.bootnative;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

/**
 * Package: me.j360.dctemplate
 * User: min_xu
 * Date: 16/6/13 下午4:09
 * 说明：参数用--xxx=xxx的形式传递
 */

@Configuration
@ComponentScan("me.j360")
@ImportResource("batch.xml")
public class Application {

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}