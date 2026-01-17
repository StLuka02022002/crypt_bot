package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.client.BinanceClientFeign;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Slf4j
public class CryptoCurrencyService {
    private final AtomicReference<Double> price = new AtomicReference<>();
    private final BinanceClientFeign client;

    public CryptoCurrencyService(BinanceClientFeign client) {
        this.client = client;
    }

    public double getBitcoinPrice() throws IOException {
        if (price.get() == null) {
            price.set(client.getPrice().asDouble());
        }
        return price.get();
    }
}
