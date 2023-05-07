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

    /************************************************************************
     *                    Mailjet Client Registration                       *
     ************************************************************************/

    @Bean
    ReactiveClientRegistrationRepository mailjetClientRegistration(
            @Value("${mailjet.api.key}") String client_id,
            @Value("${mailjet.secret.key}") String client_secret,
            @Value("${mailjet.authorization-grant-type}") String authorizationGrantType)
    {
        ClientRegistration registration = ClientRegistration
                .withRegistrationId("mailjet-webclient")
                .tokenUri("https://api.mailjet.com/v3")
                .clientId(client_id)
                .clientSecret(client_secret)
                .authorizationGrantType(new AuthorizationGrantType(authorizationGrantType))
                .build();
        return new InMemoryReactiveClientRegistrationRepository(registration);
    }

    @Bean
    WebClient mailjetWebClient(ReactiveClientRegistrationRepository mailjetClientRegistration) {
        InMemoryReactiveOAuth2AuthorizedClientService mailjetClientService = new InMemoryReactiveOAuth2AuthorizedClientService(mailjetClientRegistration);
        AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager authorizedClientManager = new AuthorizedClientServiceReactiveOAuth2AuthorizedClientManager(mailjetClientRegistration, mailjetClientService);
        ServerOAuth2AuthorizedClientExchangeFilterFunction oauth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
        oauth.setDefaultClientRegistrationId("mailjet-webclient");
        return WebClient.builder()
                .baseUrl(mailjetApiBaseUrl)
                .filter(oauth)
                .build();

    }
}
