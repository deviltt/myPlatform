package com.timetask.beans;

import org.springframework.beans.factory.FactoryBean;

import java.util.ArrayList;

public class TempFactoryBean implements FactoryBean {
    ArrayList<String> list;

    public Object getObject() throws Exception {
        list = new ArrayList<String>();

        list.add("tt");
        list.add("zz");
        return list;
    }

    public Class<?> getObjectType() {
        return ArrayList.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
