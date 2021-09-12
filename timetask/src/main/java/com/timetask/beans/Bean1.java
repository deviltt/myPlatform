package com.timetask.beans;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class Bean1 implements ApplicationContextAware {
    @Autowired
    private XmlInitBean xmlInitBean;

    private ApplicationContext applicationContext;

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public void getOtherBean() {
//        XmlInitBean xmlInitBean = (XmlInitBean) applicationContext.getBean("xmlInitBean");
        System.out.println(xmlInitBean.getName() + "222");
    }

    private void initMethod() {
        System.out.println("bean1 init method");
    }
}
