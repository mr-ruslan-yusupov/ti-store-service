package com.telmaneng.tistore.controller;

import com.telmaneng.tistore.service.TiStoreInventoryPricingImpl;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.telmaneng.tistore")
public class TiStoreInventoryPricingController {
    private final TiStoreInventoryPricingImpl tiStoreInventoryPricingImpl;

    public TiStoreInventoryPricingController(TiStoreInventoryPricingImpl tiStoreInventoryPricingImpl) {
        this.tiStoreInventoryPricingImpl = tiStoreInventoryPricingImpl;
    }

    @GetMapping(value = "/store/product/{tiPartNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStoreProductByPartNumber(@PathVariable String tiPartNumber){
        return ResponseEntity.ok(tiStoreInventoryPricingImpl.getStoreProductByPartNumber(tiPartNumber));
    }

    @GetMapping(value = "/store/products", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> getStoreProducts(@RequestParam(name="gpn", required = true) String gpn,
                                                   @RequestParam(name="page", required = true) Integer page,
                                                   @RequestParam(name="size", required = true) Integer size,
                                                   @RequestParam(name="currency", required = false, defaultValue = "USD") String currency,
                                                   @RequestParam(name="excludeEvms", required = false, defaultValue = "true") Boolean excludeEvms)
    {
        return ResponseEntity.ok(tiStoreInventoryPricingImpl.getStoreProducts(gpn,page,size,currency,excludeEvms));
    }

}
