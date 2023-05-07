package com.telmaneng.tistore;

import com.telmaneng.tistore.pojo.TiPartRequest;
import com.telmaneng.tistore.service.TiStoreAppService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TiStoreServiceApplication implements CommandLineRunner {
    private final TiStoreAppService tiStoreAppService;

    public TiStoreServiceApplication(TiStoreAppService tiStoreAppService) {
        this.tiStoreAppService = tiStoreAppService;
    }

    public static void main(String[] args) {
        SpringApplication.run(TiStoreServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        TiPartRequest tiPartRequest = new TiPartRequest();
        tiPartRequest.setCustomerName("'Teeelmaaan'");
        tiPartRequest.setCustomerEmail("'telman.yusupov@gmail.com'");
        tiPartRequest.setTiPartNumber("'AFE7799IABJ'");
        tiPartRequest.setCustomerPhoneNumber("'052-3764452'");

        tiPartRequest = tiStoreAppService.addTiPartRequest(tiPartRequest);

        tiPartRequest = new TiPartRequest();
        tiPartRequest.setCustomerName("'Ruslaaan !!!'");
        tiPartRequest.setCustomerEmail("'mr.ruslan.yusupov@gmail.com'");
        tiPartRequest.setTiPartNumber("'BFF7799IABC'");
        tiPartRequest.setCustomerPhoneNumber("'052-5093585'");
        tiPartRequest.setInStock(true);
        tiPartRequest.setIsNotified(true);

        tiPartRequest = tiStoreAppService.addTiPartRequest(tiPartRequest);
    }
}
