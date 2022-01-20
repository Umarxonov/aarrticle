package com.example.article.controller;

import com.example.article.entity.Article;
import com.example.article.entity.User;
import com.example.article.payload.*;
import com.example.article.secret.CurrentUser;
import com.example.article.servise.ArticleService;
import com.example.article.servise.StatusArticleService;
import com.example.article.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/article")

public class ArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    StatusArticleService statusArticleService;

    @PostMapping("/addArticle")
    public HttpEntity<ApiResponse> save(@RequestBody ArticleDto articleDto, @CurrentUser User user) {
        ApiResponse apiResponse = articleService.addArticle(articleDto, user);
        return ResponseEntity.status(apiResponse.isSuccess() ? 201 : 409).body(apiResponse);
    }




    @GetMapping("/getMyArticle")
    public HttpEntity<?> getMyArticle(@CurrentUser User user) {
        return ResponseEntity.ok(articleService.getMyArticle(user));
    }

    /**
     *maqollarni holatiga qarab get qilish
     */
    @PostMapping("/newMyArticle")
    public ApiResponse newMyArticle(@CurrentUser User user,@RequestBody ArticleStatusInAdmins articleStatusInAdmins) {
        return articleService.newMyArticle(user,articleStatusInAdmins);
    }


    @DeleteMapping("/deleteArticle/{id}")
    public HttpEntity<?> deleteMyArticle( @PathVariable UUID id) {
        ApiResponse apiResponse = articleService.deleteArticle(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PostMapping("/articleAddEditors")
    public HttpEntity<ApiResponse> sendNotification(@CurrentUser User user, @RequestBody NotificationForRedacktors notification) {
        ApiResponse apiResponse = articleService.articleAddEditors(user, notification);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);


    }

    @PostMapping("/articleRemoveEditor")
    public HttpEntity<ApiResponse> removeEditor(@CurrentUser User user, @RequestBody NotificationForRedacktors notification) {
        ApiResponse apiResponse = articleService.removeEditor(user, notification);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);

    }


    @PostMapping("/addAndRemoveRedactor")
    public HttpEntity<ApiResponse> addAndRemoveRedactor(@CurrentUser User user, @RequestBody AddRedactorDto addRedactorDto) {
        ApiResponse apiResponse = articleService.addAndRemoveRedactor(user, addRedactorDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    @PostMapping("/reviewerAcceptTheArticle")
    public HttpEntity<ApiResponse> reviewerAcceptTheArticle(@CurrentUser User user, @RequestBody ReviewerAndRedactorResponseDto redactorResponseDto) {
        ApiResponse apiResponse = articleService.reviewerAcceptTheArticle(user, redactorResponseDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


    //    @PostMapping("/editorAndReviewerResponsesToTheArticle")
//    public HttpEntity<ApiResponse> getArticleInformation(@CurrentUser User user,@RequestBody ReviewerAndRedactorResponseDto redactorResponseDto) {
//        ApiResponse apiResponse = articleService.editorAndReviewerResponsesToTheArticle(user,redactorResponseDto);
//
//        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
//                                                                          .equals("Saved") ? 201 : 202 : 409)
//                             .body(apiResponse);
//
//    }
    @PostMapping("/sendMassageDeadlineRedactorReviewer")
    public HttpEntity<ApiResponse> sendMassageDeadlineRedactorReviewer(@CurrentUser User user, @RequestBody AddRedactorDto addRedactorDto) {
        ApiResponse apiResponse = articleService.sendMassageDeadlineRedactorReviewer(user, addRedactorDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                        .equals("Saved") ? 201 : 202 : 409)
                .body(apiResponse);

    }

    @PostMapping("/statusesGivenToTheArticleByTheEditors")
    public HttpEntity<ApiResponse> statusesGivenToTheArticleByTheEditors(@CurrentUser User user, @RequestBody ReviewerAndRedactorResponseDto redactorResponseDto) {
        ApiResponse apiResponse = articleService.statusesGivenToTheArticleByTheEditors(user, redactorResponseDto);

        return ResponseEntity.status(apiResponse.isSuccess() ? apiResponse.getMessage()
                        .equals("Saved") ? 201 : 202 : 409)
                .body(apiResponse);

    }



    @GetMapping("/distributed")
    public List<Article> getDistributedArticles(@CurrentUser User user) {
        return articleService.getDistributeds(user);

    }

    @PostMapping("/getReviewerAndRedactorRandom")
    public ApiResponse getReviewerAndRedactorRandom(@RequestBody GetUsersRoleId getUsersRoleId){
        return articleService.getReviewerAndRedactorRandom(getUsersRoleId);
    }


//
//    @GetMapping("/getNewOllArticle")
//    public ApiResponse getNewOllArticle(){
//        return articleService.getNewOllArticle();
//    }


//    @PostMapping("/myDuties")
//    public ApiResponse myDuties(@CurrentUser  User user){
//        return articleService.myDuties(user);
//    }

}

