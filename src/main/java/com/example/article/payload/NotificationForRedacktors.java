package com.example.article.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NotificationForRedacktors {

    private List<UUID> redactorsAndReviewer;  // bu idlar userlar idsi shu kelgan idlarag article biriktiriladi

    private UUID article;   // bu article id shu articl idsi  redactorsAndReviewer idlarga biriktiriladi
    private String roleName;
//private  String massage;


}
