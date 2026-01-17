package com.skillbox.cryptobot.client;

import feign.Client;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLSocketFactory;

@Configuration
public class BinanceClientConfig {

    @Bean
    public Client feignClient() {
        return new Client.Default((SSLSocketFactory) SSLSocketFactory.getDefault(),
                (hostname, session) -> true
        );
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ErrorDecoder.Default();
    }
}