package com.telmaneng.tistore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telmaneng.tistore.pojo.MailjetEmailMessage;
import com.telmaneng.tistore.pojo.TiPartOrder;
import com.telmaneng.tistore.repository.TiRequestRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@ComponentScan("com.telmaneng.tistore.repository")
public class TiStoreAppService {
    private final Logger logger = LoggerFactory.getLogger(TiStoreAppService.class);

    private Set<String> productsToCheckSet = new HashSet<>(Arrays.asList("AFE7799IABJ", "LMZ31710RVQR"));

    private final TiStoreInventoryPricingImpl tiStoreInventory;
    private final TiRequestRepository tiRequestRepository;
    private final MailjetImpl mailjetService;

    public TiStoreAppService(TiStoreInventoryPricingImpl tiStoreInventory, TiRequestRepository tiRequestRepository, MailjetImpl mailjetService) {
        this.tiStoreInventory = tiStoreInventory;
        this.tiRequestRepository = tiRequestRepository;
        this.mailjetService = mailjetService;
    }

    public TiPartOrder addTiPartRequest(TiPartOrder tiPartOrder) {
        return tiRequestRepository.saveAndFlush(tiPartOrder);
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
            jsonStr = tiStoreInventory.getStoreProductByPartNumber(tiPartNumber);

            try {
                productJsonNode = mapper.readTree(jsonStr);
                int productQuantity = productJsonNode.get("quantity").asInt();
                if (productQuantity > 0) {
                    logger.info("Product {} is available now .", tiPartNumber);

                    String description = productJsonNode.get("description").asText();
                    String buyNowUrl = productJsonNode.get("buyNowUrl").asText();

                    MailjetEmailMessage message = mailjetService.createEmailMessage(tiPartNumber,description,productQuantity,buyNowUrl);
                    String response = mailjetService.sendEmail(message);
                    logger.info("TiStore app - Sending email response: {}", response);
                }
            }
            catch (JsonProcessingException e) {
                logger.error(e.getMessage());
            }
//            System.out.println(jsonStr);
        }
    }

}
