package com.timetask.create;

import com.timetask.beans.MainTask;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class CreateBeans {
    @Bean
//    @Scope
    public MainTask getMainTask() {
        return new MainTask();
    }

    @PostConstruct
    public void mainTaskCons(){
        System.out.println("aaa");
    }
}
