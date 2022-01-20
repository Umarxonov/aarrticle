package com.example.article.controller;

import com.example.article.entity.User;
import com.example.article.payload.ApiResponse;
import com.example.article.secret.CurrentUser;
import com.example.article.servise.ArticleService;
import com.example.article.servise.StatusArticleService;
import com.example.article.utils.AppConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/articleStatus")
public class StatusArticleController {
    @Autowired
    ArticleService articleService;

    @Autowired
    StatusArticleService statusArticleService;


    @PostMapping("/changeActiveAndFalse/{id}/{active}")
    public ApiResponse changeActiveAndFalse(@PathVariable UUID id, @CurrentUser User user, @PathVariable boolean active) {


        return new ApiResponse("change", true, statusArticleService.changeActiveAndFalse(user, active, id));
    }


    @PostMapping("/confirm/{id}/{confirm}")
    public ApiResponse confirm(@PathVariable UUID id, @CurrentUser User user, @PathVariable boolean confirm) {
        return  statusArticleService.confirm(user, confirm, id);
    }


//    @GetMapping("getStatusFilterArticle")
//    public HttpEntity<?> getStatusArticle(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
//                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size,
//                                          @RequestBody String roleName
//    ) {
//
//
//        return ResponseEntity.ok(statusArticleService.getStatusFilterArticle(page, size, roleName));
//    }


    @GetMapping("/getPayedArticle")
    public HttpEntity<?> getPayedArticle(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                         @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(statusArticleService.getPayedArticle(page, size));
    }


    @GetMapping("/getActiveTrue")
    public HttpEntity<?> getActiveArticle(@RequestParam(value = "page", defaultValue = AppConstants.DEFAULT_PAGE_NUMBER) Integer page,
                                          @RequestParam(value = "size", defaultValue = AppConstants.DEFAULT_PAGE_SIZE) Integer size) {
        return ResponseEntity.ok(statusArticleService.getActiveTrueArticle(page, size));
    }


}
