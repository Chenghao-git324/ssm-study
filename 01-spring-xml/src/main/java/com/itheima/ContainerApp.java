package com.itheima;

import com.itheima.dao.BookDao;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class ContainerApp {

    public static void main(String[] args) {
        System.out.println("===== BeanFactory（懒加载）=====");
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("beans-container.xml");
        System.out.println("BeanFactory 创建完成（此时应该还没有构造方法打印）");

        BookDao bookDao = beanFactory.getBean("bookDao", BookDao.class);
        System.out.println("getBean 之后：" + bookDao);

        System.out.println("\n===== ApplicationContext（预加载）=====");
        ClassPathXmlApplicationContext ctx =
                new ClassPathXmlApplicationContext("beans-container.xml");
        System.out.println("ApplicationContext 创建完成（此时构造方法已经打印过了）");

        ctx.close();
    }
}

