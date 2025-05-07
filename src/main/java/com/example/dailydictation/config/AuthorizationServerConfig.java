package com.example.dailydictation.config;

import com.example.dailydictation.service.CustomOAuth2UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

@Configuration
@EnableWebSecurity
public class AuthorizationServerConfig {
    @Value("${jwt.signerKey}")
    private String signerKey;
    private final String[] PUBLIC_ENDPOINT = {"/api/create-user", "auth/test-log-in","/api/get-course","/api/check-sentence","/api/get-audio-sentence","/api/comment","/api/get-transcript","/api/get-main-audio"};
    private final String[] PRIVATE_ENDPOINT = {"/api/get-all-user","/api/create-course"};
    @Bean
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(request ->
                request.requestMatchers(HttpMethod.POST, PUBLIC_ENDPOINT).permitAll()// config public api
                        .requestMatchers(HttpMethod.GET,PRIVATE_ENDPOINT).hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.POST,PRIVATE_ENDPOINT).hasAuthority("SCOPE_ADMIN")
                        .requestMatchers(HttpMethod.GET, PUBLIC_ENDPOINT).permitAll()
                        .anyRequest().authenticated());
//
        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwtConfigurer -> jwtConfigurer.decoder(jwtDecoder())));


        http.oauth2Login(oauth2 -> oauth2
                .defaultSuccessUrl("/auth/success", true)
                .userInfoEndpoint(userInfo -> userInfo
                        .userService(oauth2UserService())
                )
        );

        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        SecretKeySpec secretKeySpec = new SecretKeySpec(signerKey.getBytes(), "SH512");
        return NimbusJwtDecoder
                .withSecretKey(secretKeySpec)
                .macAlgorithm(MacAlgorithm.HS512)
                .build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
     return new BCryptPasswordEncoder(10);
    }
    @Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new CustomOAuth2UserService();
    }

}
