package com.telmaneng.tistore.controller;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ComponentScan("com.telmaneng.tistore")
public class TiStoreAppController {

    @GetMapping(value = "/", produces = MediaType.TEXT_PLAIN_VALUE)
    public String home() {
        return "\n[Application info]" +
                "\nApplication name : TI Store Application";
    }

}
