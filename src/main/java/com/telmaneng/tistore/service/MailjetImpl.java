package com.telmaneng.tistore.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class MailjetImpl {
    private final Logger logger = LoggerFactory.getLogger(MailjetImpl.class);
    private final WebClient mailjetWebClient;

    public MailjetImpl(WebClient mailjetWebClient) {
        this.mailjetWebClient = mailjetWebClient;
    }

    public void sendEmail() {
        //
    }
}
