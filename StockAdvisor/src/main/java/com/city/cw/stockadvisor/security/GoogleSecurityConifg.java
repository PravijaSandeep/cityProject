package com.city.cw.stockadvisor.security;

import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(OAuth2ClientProperties.class)
public class GoogleSecurityConifg {
    
    
    public ClientRegistrationRepository clientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
        return new CustomClientRegistrationRepository(oAuth2ClientProperties);
    }

    private static class CustomClientRegistrationRepository implements ClientRegistrationRepository {
        private final ClientRegistration clientRegistration;

        CustomClientRegistrationRepository(OAuth2ClientProperties oAuth2ClientProperties) {
            OAuth2ClientProperties.Registration registration = oAuth2ClientProperties.getRegistration().get("google");
            this.clientRegistration = ClientRegistration.withRegistrationId("google")
                .clientId(registration.getClientId())
                .clientSecret(registration.getClientSecret())
                .clientAuthenticationMethod(ClientAuthenticationMethod.BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .redirectUriTemplate("{baseUrl}/login/oauth2/code/{registrationId}")
                .scope("openid", "profile", "email")
                .authorizationUri("https://accounts.google.com/o/oauth2/auth")
                .tokenUri("https://www.googleapis.com/oauth2/v3/token")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .userNameAttributeName("email")
                .jwkSetUri("https://www.googleapis.com/oauth2/v3/certs")
                .build();
        }

        @Override
        public ClientRegistration findByRegistrationId(String registrationId) {
            return "google".equals(registrationId) ? clientRegistration : null;
        }
    }
}

