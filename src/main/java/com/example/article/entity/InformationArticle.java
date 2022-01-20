package com.example.article.entity;

import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.enums.Watdou;
import com.example.article.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class InformationArticle extends AbsEntity {  //bu articldi kim va qachon

    // activ yoki qabul qilgan tebl userlarni qachon articlga murojat qilganini saqlab boradi

    // activ yoki qabul qilgan tebl


//    long deadline=10000L;

    @ManyToOne
    private User chekUser;  // qaysi user ozgartirdi

    @ManyToOne(fetch = FetchType.EAGER)
    private Article article;  // qaysi articildi

    @ManyToOne
    private User redactor;  // qaysi redactor yo Reviewer larni   qoshdi yoki o'chirdi

    private Date whenAndWho;   // qachon o'zgartirdi'

    @Enumerated(EnumType.STRING)
    private Watdou watdou;  // qaysi toifaga ozgartirdi


    @Enumerated(EnumType.STRING)
    private ArticleStatusName articleStatusName;   //redaktor qaysi statusni berdi redactorlar tomondan bertiladigon statuslar

    private String massage;

    private  long deadline;

    public InformationArticle(User user, Article byId, Date date, Watdou watdou) {
        this.chekUser = user;
        this.article = byId;
        this.watdou = watdou;
        this.whenAndWho = date;
    }

    public InformationArticle(User user, Article articleId, Date date, ArticleStatusName articleStatus) {
        this.redactor = user;
        this.article = articleId;
        this.articleStatusName = articleStatus;
        this.whenAndWho = date;
    }

    public InformationArticle(User user, User redactors, Article byId, Date date, Watdou watdou) {
        this.chekUser = user;
        this.redactor = redactors;
        this.article = byId;
        this.watdou = watdou;
        this.whenAndWho = date;
    }

    public InformationArticle(User user, Article byId, Date date, Watdou watdou, String massage) {
        this.chekUser = user;
        this.article = byId;
        this.whenAndWho = date;
        this.watdou = watdou;
        this.massage = massage;
    }

    public InformationArticle(User chekUser, Article article, Date whenAndWho, ArticleStatusName articleStatusName, String massage) {
        this.redactor = chekUser;
        this.article = article;
        this.whenAndWho = whenAndWho;
        this.articleStatusName = articleStatusName;
        this.massage = massage;
    }

    public InformationArticle( User redactor,Article article, ArticleStatusName articleStatusName) {
        this.article = article;
        this.redactor = redactor;
        this.articleStatusName = articleStatusName;
    }
}
