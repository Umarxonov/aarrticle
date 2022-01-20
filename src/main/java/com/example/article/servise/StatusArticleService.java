package com.example.article.servise;

import com.example.article.entity.Article;
import com.example.article.entity.InformationArticle;
import com.example.article.entity.Role;
import com.example.article.entity.User;
import com.example.article.entity.enums.ArticleStatusName;
import com.example.article.entity.enums.Watdou;
import com.example.article.payload.ApiResponse;
import com.example.article.repository.ArticleRepository;
import com.example.article.repository.InformationArticleRepository;
import com.example.article.utils.CommonUtills;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class StatusArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    InformationArticleRepository informationArticleRepository;

    // activini true false qiladi
    public ApiResponse changeActiveAndFalse(User user, boolean changeStatus, UUID id) {

//        findFirstByArticleIdOrderByCreatedAtDesc agar ishlamasa findFirstByArticleIdOrderByCreatedAtAsc ni qo'llab ko'ring

        if (user.getId().equals(informationArticleRepository.findFirstByArticleIdOrderByCreatedAtDesc(id).getChekUser().getId())) {

            Article byId = articleRepository.getById(id);
            byId.setActive(changeStatus);
            articleRepository.save(byId);
            return new ApiResponse(byId.isActive() ? "Activated" : "Blocked", true);
        }else {
            return new ApiResponse("sizga mumkin emas " , false);
        }

    }

    // bu articldi kim qabul qilganini aniqlidi
    public ApiResponse confirm(User user, boolean confirm, UUID id) {
        String roles="";
        Article byId = articleRepository.getById(id);
        if (byId.isConfirm()==confirm)
            return new ApiResponse("Article already "+(confirm? "confirmed":"not confirmed"));
        byId.setConfirm(confirm);
        byId.setArticleStatusName(ArticleStatusName.START);
        articleRepository.save(byId);

        for (Role role : user.getRoles()) {
            roles=role.getRoleName();
        }
        informationArticleRepository.save(new InformationArticle(user, byId, new Date(), confirm ? Watdou.CONFIRM : Watdou.UN_CONFIRM,
                                                                 roles+": "+user.getLastName()+" "+user.getFirstName()+" tomonidan maqola aktivlashtiril"+(confirm?"":"ma")+"di" ));
        return new ApiResponse(byId.isConfirm() ? "confirmed" : "not confirmed", true);


    }

    //bunda article stusi beriladi shu boyicha sort qilinadi
//    public ApiResponse getStatusFilterArticle(Integer page, Integer size, String roleName) {
//        try {
//
//            Page<Article> articlePage = articleRepository.findAllByChecked(roleName, CommonUtills.simplePageable(page, size));
//
//            return new ApiResponse("all", true, articlePage);
//        } catch (Exception e) {
//            return new ApiResponse("not found", false);
//        }
//    }

    // bu puli to'langan articlani get qilib beradi
    public ApiResponse getPayedArticle(Integer page, Integer size) {
        try {

            Page<Article> articlePage = articleRepository.findByPayTrue(CommonUtills.simplePageable(page, size));

            return new ApiResponse("all", true, articlePage);
        } catch (Exception e) {
            return new ApiResponse("not found", false);
        }


    }


    // bu activ articlani get qilib beradi

    // bu activ articlanio get qilib beradi

    public ApiResponse getActiveTrueArticle(Integer page, Integer size) {
        try {

            Page<Article> articlePage = articleRepository.findByActiveTrue(CommonUtills.simplePageable(page, size));

            return new ApiResponse("all", true, articlePage);
        } catch (Exception e) {
            return new ApiResponse("not found", false);
        }
    }


}
