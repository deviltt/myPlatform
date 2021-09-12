package com.tt.designpattern.observer;

import java.util.ArrayList;
import java.util.List;

public class ObserverPattern {
    public static void main(String[] args) {
        Subject subject = new ConcreteSubject();
        Observer observer1 = new ConcreteObserver1();
        Observer observer2 = new ConcreteObserver2();

        subject.add(observer1);
        subject.add(observer2);

        subject.notifyObserver();
    }
}

abstract class Subject {
    protected List<Observer> observerList = new ArrayList<>();

    // 添加观察者
    public void add(Observer observer) {
        observerList.add(observer);
    }

    // 删除观察者
    public void remove(Observer observer) {
        observerList.remove(observer);
    }

    // 通知观察者
    public abstract void notifyObserver();
}

class ConcreteSubject extends Subject {
    public void notifyObserver() {
        System.out.println("具体目标发生改变...");
        System.out.println("==========================");
        for (Observer observer : observerList) {
            observer.response();
        }
    }
}

interface Observer {
    void response();
}

class ConcreteObserver1 implements Observer {
    public void response() {
        System.out.println("具体观察者1做出反应！");
    }
}

class ConcreteObserver2 implements Observer {
    public void response() {
        System.out.println("具体观察者2做出反应！");
    }
}
