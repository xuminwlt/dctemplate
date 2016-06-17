package me.j360.bootnative.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * Package: me.j360.bootnative.service
 * User: min_xu
 * Date: 16/6/17 上午11:46
 * 说明：
 */

@Service
public class UserService {


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
}
