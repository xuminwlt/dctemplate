package me.j360.dctemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Package: me.j360.dctemplate
 * User: min_xu
 * Date: 16/6/16 下午4:10
 * 说明：
 */

@Component
public class MyBean {

    @Autowired
    public MyBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
        // if run with "--debug logfile.txt" debug=true, files=["logfile.txt"]

        System.out.println("--"+args.getSourceArgs());
    }

}
