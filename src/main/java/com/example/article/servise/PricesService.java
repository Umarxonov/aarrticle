package com.example.article.servise;

import com.example.article.entity.Prices;
import com.example.article.payload.ApiResponse;
import com.example.article.payload.PriceDTO;
import com.example.article.repository.PricesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PricesService {
    @Autowired
    PricesRepository pricesRepository;

    public ApiResponse edit(PriceDTO priceDTO){
            Optional<Prices> optionalPrices = pricesRepository.findById(priceDTO.getId());
            if (optionalPrices.isPresent()){
                Prices prices = optionalPrices.get();
                prices.setPriceOfArticle(priceDTO.getPriceOfArticle());
                pricesRepository.save(prices);
                return new ApiResponse("Successfully edited", true);
            }
            return new ApiResponse("Not found", false);

    }
}
