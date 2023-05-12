package com.telmaneng.tistore.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.telmaneng.tistore.pojo.MailjetEmailMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class MailjetImpl {
    private final Logger logger = LoggerFactory.getLogger(MailjetImpl.class);
    private final WebClient mailjetWebClient;

    public MailjetImpl(WebClient mailjetWebClient) {
        this.mailjetWebClient = mailjetWebClient;
    }


    public MailjetEmailMessage createEmailMessage(String tiPartNumber, String tiPartDescription, int tiPartQuantity, String buyTiPartUrl) {
        MailjetEmailMessage mailjetEmailMessage = new MailjetEmailMessage();
        mailjetEmailMessage.setFromEmail("mr.ruslan.yusupov@gmail.com");
        mailjetEmailMessage.setFromName("Ruslan");
        mailjetEmailMessage.setSubject("Products in stock notification");

        //mailjetEmailMessage.setPlainTextBody("My plain text");
        //mailjetEmailMessage.setHtmlTextBody("<html><body>My html text</body></html>");

        StringBuilder messageBodyHtml = new StringBuilder();
        messageBodyHtml.append("<html><body>");
        messageBodyHtml.append("Product ").append(tiPartNumber).append(" ").append(tiPartDescription).append(" ").append("is available in stock.");
        messageBodyHtml.append("<br>");
        messageBodyHtml.append("Quantity in stock ").append(tiPartQuantity).append(" pieces .");
        messageBodyHtml.append("<br>");
        messageBodyHtml.append("To buy this product, click on link below:");
        messageBodyHtml.append("<br>");
        messageBodyHtml.append("<a href='").append(buyTiPartUrl).append("'>").append(buyTiPartUrl).append("</a>");
        messageBodyHtml.append("</body></html>");

        mailjetEmailMessage.setPlainTextBody(messageBodyHtml.toString());
        mailjetEmailMessage.setHtmlTextBody(messageBodyHtml.toString());

        //mailjetEmailMessage.addRecipient("telman.yusupov@gmail.com","Telman");
        mailjetEmailMessage.addRecipient("tankist.teddy@gmail.com","Teddy");

        return mailjetEmailMessage;
    }

    public String sendEmail(MailjetEmailMessage mailjetEmailMessage) {
        String response;
        ObjectMapper mapper = new ObjectMapper();
        try {
            String jsonRequest = mapper.writeValueAsString(mailjetEmailMessage);
            logger.info("TiStore app - Sending email. JSON: {}", jsonRequest);

            //TODO - call Mailjet API
            response = mailjetWebClient
                    .post()
                    .uri("/send")
                    .contentType(MediaType.APPLICATION_JSON)
                    .accept(MediaType.APPLICATION_JSON)
                    .bodyValue(jsonRequest)
                    //.body(Mono.just(jsonRequest), String.class) <-- alternative way
                    .retrieve()
                    .bodyToMono(String.class)
                    .block(Duration.ofSeconds(90));
        }
        catch (JsonProcessingException e) {
            e.printStackTrace();
            response = e.getMessage();
        }
        return response;
    }
}
