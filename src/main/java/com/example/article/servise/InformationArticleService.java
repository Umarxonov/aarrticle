package com.example.article.servise;

import com.example.article.entity.InformationArticle;
import com.example.article.entity.TimeLine;
import com.example.article.entity.User;
import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.enums.Watdou;
import com.example.article.repository.InformationArticleRepository;
import com.example.article.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class InformationArticleService {
    @Autowired
    InformationArticleRepository informationArticleRepository;

    @Autowired
    UserRepository userRepository;
    public List<TimeLine> getByArticleId(UUID articleId){
        List<TimeLine> timeLineList=new ArrayList<>();
        List<InformationArticle> informationArticleList = informationArticleRepository.findAllByArticleIdAndMassageNotNullOrderByCreatedAt(articleId);
        for (InformationArticle article : informationArticleList) {
            Date date=new Date(article.getCreatedAt().getTime());
            DateFormat dateFormat=new SimpleDateFormat("yyyy-MMM-dd HH:mm");
            String processDate= dateFormat.format(date);
            TimeLine timeLine=new TimeLine();
            timeLine.setProcessDate(processDate);
            timeLine.setMessage(article.getMassage());
            timeLineList.add(timeLine);
        }
        return timeLineList;
    }


    public List<InformationArticle> getByBetween(long start, long end){
        Timestamp startDate=new Timestamp(start);
        Timestamp endDate=new Timestamp(end);
        return informationArticleRepository.findAllByCreatedAtBetween(startDate,endDate);
    }

    public List<InformationArticle>  getByRedactorIdAndCreatedDateBetween(UUID redactorId, long startDate, long endDate){
        Timestamp start=new Timestamp(startDate);
        Timestamp end=new Timestamp(endDate);
        return informationArticleRepository.findAllByRedactorIdAndCreatedAtIsBetweenOrderByCreatedAtDesc(redactorId,start,end);
    }

    public List<InformationArticle> getDistributeds(User user) {
        List<InformationArticle> informationArticleList = informationArticleRepository.findAllByChekUserIdAndWatdou(user.getId(), Watdou.ADD);
        List<UUID> uuidList=new ArrayList<>();
        for (InformationArticle article : informationArticleList) {
            UUID id = article.getArticle().getId();
            uuidList.add(id);
        }
        return informationArticleRepository.findAllByArticleIdAndArticleStatusNameOrderByCreatedAtDesc(uuidList, ArticleStatusName.I_ACCEPTED.toString());
    }

}
