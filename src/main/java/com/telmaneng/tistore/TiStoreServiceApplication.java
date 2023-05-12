package com.telmaneng.tistore;

import com.telmaneng.tistore.pojo.TiPartOrder;
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
//        TiPartOrder tiPartOrder = new TiPartOrder();
//        tiPartOrder.setCustomerName("'Teeelmaaan'");
//        tiPartOrder.setCustomerEmail("'telman.yusupov@gmail.com'");
//        tiPartOrder.setTiPartNumber("'AFE7799IABJ'");
//        tiPartOrder.setCustomerPhoneNumber("'052-3764452'");
//
//        tiPartOrder = tiStoreAppService.addTiPartRequest(tiPartOrder);
//
//        tiPartOrder = new TiPartOrder();
//        tiPartOrder.setCustomerName("'Ruslaaan !!!'");
//        tiPartOrder.setCustomerEmail("'mr.ruslan.yusupov@gmail.com'");
//        tiPartOrder.setTiPartNumber("'BFF7799IABC'");
//        tiPartOrder.setCustomerPhoneNumber("'052-5093585'");
//        tiPartOrder.setInStock(true);
//
//        tiPartOrder = tiStoreAppService.addTiPartRequest(tiPartOrder);
    }
}
