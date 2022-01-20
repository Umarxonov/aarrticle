package com.example.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Date;


@SpringBootApplication
//@EnableScheduling
public class ArticleApplication {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApplication.class, args);
        Date date=new Date();
        System.out.println("push notification"+date.getTime());
    }

}
