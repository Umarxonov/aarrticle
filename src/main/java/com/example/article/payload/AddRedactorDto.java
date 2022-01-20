package com.example.article.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddRedactorDto {


    private UUID redactorsAndReviewer;  // bu idlar userlar idsi shu kelgan idlarag article biriktiriladi

    private UUID article;   // bu article id shu articl idsi  redactorsAndReviewer idlarga biriktiriladi
    private boolean addAndRemove;

    private String massage;
    private long  deadline;
    private Integer role;

}
