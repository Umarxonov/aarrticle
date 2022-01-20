package com.example.article.entity;

import com.example.article.entity.template.AbsEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class
OferOfArticle extends AbsEntity {

    private String description;

    @ManyToOne
    private User admin;

    @ManyToOne
    private Article article;

    private boolean delete;

}
