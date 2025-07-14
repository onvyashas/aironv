package org.yashas.AirlineManagement.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // All requests are permitted as no specific authority or role checks are required.
        http
            .authorizeHttpRequests(requests -> 
                requests.anyRequest().permitAll() 
            )
            .csrf(csrf -> csrf.disable()) 
            .headers(headers -> 
                headers.frameOptions(options -> options.sameOrigin()) 
            );
        
        return http.build(); 
    }
}