package com.example.article.controller;

import com.example.article.entity.InformationArticle;
import com.example.article.entity.TimeLine;
import com.example.article.entity.User;
import com.example.article.secret.CurrentUser;
import com.example.article.servise.ArticleService;
import com.example.article.servise.InformationArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/infoArticle")
public class InformationArticleController {

    @Autowired
    InformationArticleService informationArticleService;
    @Autowired
    ArticleService articleService;

    @GetMapping("/timeLine/{articleId}")
    public List<TimeLine> getByArticleId(@PathVariable UUID articleId) {
        return informationArticleService.getByArticleId(articleId);
    }

    @GetMapping("/getInfoBySelectDate")
    public List<InformationArticle> getAllByMonth(@RequestParam  long start, @RequestParam long end) {
        return informationArticleService.getByBetween(start, end);
    }

    /**
     * Ma'lum vaqt oralig'idagi bajarilgan ishlarni olib kelish uchun
     * @param redactorId
     * @param start
     * @param end
     */
    @GetMapping("/getByArticleIdAndCreatedDateBetween/{redactorId}")
    public List<InformationArticle> getByRedactorIdAndCreatedAt(@PathVariable UUID redactorId, @RequestParam long start, @RequestParam long end){
        return informationArticleService.getByRedactorIdAndCreatedDateBetween(redactorId, start, end);
    }

    /**
     * ADMIN PANELIDAGI TAQSIMLANISHLAR BO`LIMIGA
     */
    @GetMapping("/distributed")
    public List<InformationArticle> getDistributedArticles(@CurrentUser User user){
        return informationArticleService.getDistributeds(user);
    }

}
