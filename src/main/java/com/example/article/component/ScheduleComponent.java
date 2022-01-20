package com.example.article.component;

import com.example.article.entity.InformationArticle;
import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.repository.ArticleRepository;
import com.example.article.repository.InformationArticleRepository;
import com.example.article.repository.UserRepository;
import com.example.article.servise.InformationArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

//@Component
//public class ScheduleComponent {
//    @Autowired
//    InformationArticleRepository informationArticleRepository;
//    @Autowired
//    InformationArticleService informationArticleService;
//    @Autowired
//    UserRepository userRepository;
//    @Autowired
//    ArticleRepository articleRepository;

//    @Scheduled(fixedRate = 5000, initialDelay = 10000)
//    public void scheduledCategory() throws InterruptedException {
////        Category category = new Category();
////        category.setName("Schedule");
////        category = categoryRepository.save(category);
////        System.out.println(category.getName());
////        System.out.println(new Date());
//    }

//    @Scheduled(fixedRate = 14400000)
//    public void scheduleArticle() {
//        List<InformationArticle> all = informationArticleRepository.findAllByArticleStatusName(ArticleStatusName.I_ACCEPTED);
//        for (InformationArticle informationArticle : all) {
//            long deadLine  = informationArticle.getCreatedAt().getTime() + 5000L;
//            if (deadLine<=System.currentTimeMillis()){
//                UUID redactorId = informationArticle.getRedactor().getId();
//                UUID articleId = informationArticle.getArticle().getId();
//                boolean b=false;
//                List<InformationArticle> articleList = informationArticleRepository.findAllByArticleIdAndRedactorIdAndArticleStatusNameOrArticleIdAndRedactorIdAndArticleStatusNameOrArticleIdAndRedactorIdAndArticleStatusName(articleId, redactorId,ArticleStatusName.CHECK_AND_ACCEPT, articleId, redactorId,ArticleStatusName.CHECK_AND_CANCEL,articleId, redactorId,ArticleStatusName.NONE);
//                if (articleList.size()==0)
//                    b=true;
//                if (b)
//                    informationArticleRepository.save(new InformationArticle(informationArticle.getRedactor(), informationArticle.getArticle(), ArticleStatusName.NONE));
//            }
//        }
//    }
//}


