package com.telmaneng.tistore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telmaneng.tistore.pojo.MailjetEmailMessage;
import com.telmaneng.tistore.pojo.TiPartOrder;
import com.telmaneng.tistore.repository.TiOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@ComponentScan("com.telmaneng.tistore.repository")
public class TiStoreAppService {
    private final Logger logger = LoggerFactory.getLogger(TiStoreAppService.class);

    private final TiStoreInventoryPricingImpl tiStoreInventory;
    private final TiOrderRepository tiOrderRepository;
    private final MailjetImpl mailjetService;

    public TiStoreAppService(TiStoreInventoryPricingImpl tiStoreInventory, TiOrderRepository tiOrderRepository, MailjetImpl mailjetService) {
        this.tiStoreInventory = tiStoreInventory;
        this.tiOrderRepository = tiOrderRepository;
        this.mailjetService = mailjetService;
    }

    public TiPartOrder addTiPartRequest(TiPartOrder tiPartOrder) {
        return tiOrderRepository.saveAndFlush(tiPartOrder);
    }

    @Scheduled(initialDelay = 20000, fixedRate = 150000) // 150000 milliseconds = 2.5 min
    public void checkProductAvailability() {
        logger.info(">>> Start checking products availability");

        ObjectMapper mapper = new ObjectMapper();
        String jsonStr;
        JsonNode productJsonNode;

        List<String> emailsList = tiOrderRepository.getAllCustomerEmailsWithActiveOrders();

        for (String customerEmail : emailsList ) {
            List<TiPartOrder> tiPartOrderList = tiOrderRepository.findByCustomerEmailAndIsInStockFalse(customerEmail);

            for (TiPartOrder tiPartOrder : tiPartOrderList) {
                logger.info(">>> Checking product part number {} availability for customer email {}", tiPartOrder.getTiPartNumber(), customerEmail);
                jsonStr = tiStoreInventory.getStoreProductByPartNumber(tiPartOrder.getTiPartNumber());

                try {
                    productJsonNode = mapper.readTree(jsonStr);
                    int productQuantity = productJsonNode.get("quantity").asInt();
                    if (productQuantity > 0) {
                        logger.info(">>> Product part number {} is available now .", tiPartOrder.getTiPartNumber());

                        tiPartOrder.setInStock(true);
                        tiOrderRepository.saveAndFlush(tiPartOrder);

                        String description = productJsonNode.get("description").asText();
                        String buyNowUrl = productJsonNode.get("buyNowUrl").asText();

                        MailjetEmailMessage message = mailjetService.createEmailMessage(
                                tiPartOrder.getCustomerEmail(),
                                tiPartOrder.getCustomerName(),
                                tiPartOrder.getTiPartNumber(),
                                description,
                                productQuantity,
                                buyNowUrl);

                        String response = mailjetService.sendEmail(message);
                        logger.info(">>> Sending email to customer response: {}", response);
                    }
                    else {
                        logger.info(">>> Product part number {} is not available yet.", tiPartOrder.getTiPartNumber());
                    }
                }
                catch (JsonProcessingException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        logger.info(">>> End checking parts availability");
    }

}
