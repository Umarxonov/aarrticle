package com.example.article.controller;

import com.example.article.payload.ApiResponse;
import com.example.article.payload.PriceDTO;
import com.example.article.repository.PricesRepository;
import com.example.article.servise.PricesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/price")
public class PricesController {
    @Autowired
    PricesService pricesService;

    @PostMapping("/article")
    public HttpEntity<?> editPriceOfArticle(@RequestBody PriceDTO priceDTO){
        ApiResponse apiResponse = pricesService.edit(priceDTO);
        return ResponseEntity.status(apiResponse.isSuccess()?202:409).body(apiResponse);
    }
}
