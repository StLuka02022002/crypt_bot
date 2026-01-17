package com.skillbox.cryptobot.client;

import lombok.Data;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(
        name = "binance-api",
        url = "${binance.api.getPrice}",
        configuration = BinanceClientConfig.class
)
public interface BinanceClientFeign {

    @GetMapping
    PriceResponse getPrice();

    @Data
    class PriceResponse {
        private String price;

        public Double asDouble() {
            try {
                return Double.parseDouble(price);
            } catch (Exception e) {
                return null;
            }
        }
    }
}