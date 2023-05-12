package com.telmaneng.tistore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telmaneng.tistore.pojo.MailjetEmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
public class TiStoreInventoryPricingImpl {
    private final Logger logger = LoggerFactory.getLogger(TiStoreInventoryPricingImpl.class);

    private final WebClient tiWebClient;

    public TiStoreInventoryPricingImpl(WebClient tiWebClient) {
        this.tiWebClient = tiWebClient;
    }

    public String getStoreProductByPartNumber(String tiPartNumber) {
        logger.info(">>> Getting from TI server product by part number " + tiPartNumber);
        return tiWebClient.get()
                .uri("/store/products/" + tiPartNumber)
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(90));
    }

    public String getStoreProducts(String gpn, Integer page, Integer size, String currency, Boolean excludeEvms) {
//        logger.info("TiStore app - Getting products");
//        return tiWebClient.get()
//                .uri("/store/products")
//                .header("gpn", gpn)
//                .header("page",String.valueOf(page))
//                .header("size",String.valueOf(size))
//                .header("currency",currency)
//                .header("excludeEvms", String.valueOf(excludeEvms))
//                .retrieve()
//                .bodyToFlux(String.class)
//                .blockFirst(Duration.ofSeconds(90));

        return tiWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/store/products")
                        .queryParam("gpn", gpn)
                        .queryParam("page",String.valueOf(page))
                        .queryParam("size",String.valueOf(size))
                        .queryParam("currency",currency)
                        .queryParam("excludeEvms", String.valueOf(excludeEvms))
                        .build())
                .retrieve()
                .bodyToFlux(String.class)
                .blockFirst(Duration.ofSeconds(90));
    }

}
