package com.timetask.beans;

import com.timetask.create.CreateBeans;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test {
    @org.junit.Test
    public void test1() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(CreateBeans.class);

        MainTask mainTask = (MainTask) context.getBean("getMainTask");
        MainTask mainTask1 = (MainTask) context.getBean("getMainTask");

        System.out.println(mainTask == mainTask1);
        mainTask.test();
    }

    @org.junit.Test
    public void test2() {
        // 基于类加载路径
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");

        XmlInitBean xmlInitBean = (XmlInitBean) context.getBean("xmlInitBean");
//        MainTask mainTask1 = (MainTask) context.getBean("getMainTask");

//        System.out.println(mainTask == mainTask1);
        xmlInitBean.call();

        xmlInitBean.changeName("taozi1");

        xmlInitBean.call();
    }

    @org.junit.Test
    public void test3() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");

        Bean1 bean1 = (Bean1) context.getBean("bean1");
        bean1.getOtherBean();
    }

    @org.junit.Test
    public void test4() {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");

        System.out.println(context.getBean("tempFactoryBean"));
    }
}
