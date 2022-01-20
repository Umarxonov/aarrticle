package com.example.article.payload;

import com.example.article.entity.enums.ArticleStatusName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.UUID;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewerAndRedactorResponseDto {
    private UUID articleId;
    private String articleStatus;



//    private ArticleStatusName articleStatus;




}




