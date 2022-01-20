package com.example.article.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class ArticleDto {
    private UUID id;
    private String lastName;
    private String firstName;
    private String description;
    private String author;
    private String titleArticle;

    private Integer categoryId;
    private UUID fileId;

    private boolean active=false;
    private boolean confirm=false;

}
