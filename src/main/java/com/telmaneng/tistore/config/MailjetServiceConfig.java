package com.telmaneng.tistore.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryReactiveOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.InMemoryReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class MailjetServiceConfig {
    @Value("${mailjet.api.base.url}")
    private String mailjetApiBaseUrl;

    @Value("${mailjet.api.key}")
    private String clientId;

    @Value("${mailjet.secret.key}")
    private String clientSecret;


    /************************************************************************
     *                    Mailjet Client Registration                       *
     ************************************************************************/


    @Bean
    WebClient mailjetWebClient(ReactiveClientRegistrationRepository mailjetClientRegistration) {
        return WebClient.builder()
                .baseUrl(mailjetApiBaseUrl)
                .defaultHeaders(header -> header.setBasicAuth(clientId, clientSecret))
                .build();

    }
}
