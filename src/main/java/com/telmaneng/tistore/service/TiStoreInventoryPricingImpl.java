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

    private Set<String> productsToCheckSet = new HashSet<>(Arrays.asList("AFE7799IABJ", "LMZ31710RVQR"));

    private final WebClient tiWebClient;

    private final MailjetImpl mailjetService;

    public TiStoreInventoryPricingImpl(WebClient tiWebClient, MailjetImpl mailjetService) {
        this.tiWebClient = tiWebClient;
        this.mailjetService = mailjetService;
    }

    public String getStoreProductByPartNumber(String tiPartNumber) {
        logger.info("TiStore app - Getting product by part number " + tiPartNumber);
        return tiWebClient.get()
                .uri("/store/products/" + tiPartNumber)
                .retrieve()
                .bodyToMono(String.class)
                .block(Duration.ofSeconds(90));
    }

    @Scheduled(initialDelay = 20000, fixedRate = 150000) // 150000 milliseconds = 2.5 min
    public void checkProductAvailability() {
        logger.info("TiStore app - Checking products availability");

        Iterator<String> productsToCheckIterator = productsToCheckSet.iterator();

        String tiPartNumber;
        String jsonStr;

        ObjectMapper mapper = new ObjectMapper();
        JsonNode productJsonNode;

        while (productsToCheckIterator.hasNext()) {
            tiPartNumber = productsToCheckIterator.next();
            logger.info("TiStore app - Checking part number {} availability", tiPartNumber);
            jsonStr = getStoreProductByPartNumber(tiPartNumber);

            try {
                productJsonNode = mapper.readTree(jsonStr);
                int productQuantity = productJsonNode.get("quantity").asInt();
                if (productQuantity > 0) {
                    logger.info("Product {} is available now .", tiPartNumber);

                    String description = productJsonNode.get("description").asText();
                    String buyNowUrl = productJsonNode.get("buyNowUrl").asText();

                    MailjetEmailMessage message = mailjetService.createEmailMessage(tiPartNumber,description,productQuantity,buyNowUrl);
                    mailjetService.sendEmail(message);
                }
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
//            System.out.println(jsonStr);
        }
    }

    public String getStoreProducts(String gpn, Integer page, Integer size, String currency, Boolean excludeEvms) {
        logger.info("TiStore app - Getting products");
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
