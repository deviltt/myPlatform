package com.timetask.beans;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class XmlInitBean {
    @Value("tt")
    private String name;

    public void call() {
        System.out.println(name);
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
