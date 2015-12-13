package com.epam.cdp.anton.krynytskyi;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class App {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext(
                "Spring-Module.xml");

        HelloWorld obj = (HelloWorld) context.getBean("helloBean");
        obj.printHello();

        String canonicalName = obj.getClass().getSimpleName();
        System.out.println("asdasdsdsa:1465486746".split(":")[1]);
    }
}