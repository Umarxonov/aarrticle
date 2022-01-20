package com.example.article.entity;

import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.template.AbsEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Article extends AbsEntity {

    @Column(nullable = false)
    private String lastName;
    private String firstName;
    private String description;
    private String author;
    private String titleArticle;

    private long price;

    private  boolean publicAndPrivate;

    private boolean active = false;//Admin  o'zi o'zgartiradi  va edit qilishga aloqasi yoq

    private boolean pay = false;  //pul tolasa ozgaradi  va edit qilishga aloqasi yoq

    @JsonIgnore
    private boolean confirm = false; //admin qabul qilganini bildiradi keyin bu maqolami edit qilib bo'lmaydi

    private boolean rejected; // qayta ishlashga berildi adminlarning tasdiqlashi uchun


//
//    @ManyToMany
//    private List<ArticleStatus> articleStatuses;

    public Article(String lastName, Category category) {
        this.lastName = lastName;
        this.category = category;
    }


    @Enumerated(EnumType.STRING)
    private ArticleStatusName articleStatusName;

//    private String status;

    @ManyToOne
    private User admin;

    @OneToOne(fetch = FetchType.LAZY)
    private Attachment file;

    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

}
