package com.example.article.entity;

import com.example.article.entity.template.AbsEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import java.sql.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class EditorsArticle extends AbsEntity {

    @ManyToOne
    private User redactor; // qaysi userlarni biriktirdi

    @ManyToOne
    private User whoAdd;  // kim biriktirdi

    @ManyToOne
    private Article article;

    private Date deadline;
    private String massage;

    private Integer roleId;


    private boolean did = false;

    public EditorsArticle(User whoAdd, User redactor, Article article,Integer roleId) {
        this.whoAdd = whoAdd;
        this.redactor = redactor;
        this.article = article;
        this.roleId = roleId;


    }

    public EditorsArticle(User whoAdd, Date deadline, String massage) {
        this.whoAdd = whoAdd;
        this.deadline = deadline;
        this.massage = massage;

    }


    private boolean finish = false;


}
