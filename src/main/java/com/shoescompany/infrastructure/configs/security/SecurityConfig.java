package com.shoescompany.infrastructure.configs.security;

import com.shoescompany.application.services.implementations.UserDetailServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http -> {

                    // Permitir acceso público a Swagger UI y recursos relacionados
                    http.requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui/index.html").permitAll();
                    // Permitir acceso público a ciertos GET endpoints
                    http.requestMatchers(HttpMethod.GET, "/v1/category/{id}", "/v1/categories").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/v1/brand", "/v1/brands").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/v1/color", "/v1/colors").permitAll();
                    http.requestMatchers(HttpMethod.GET, "/v1/product", "/v1/products").permitAll();
                    // Requiere autenticación para otros endpoints POST, PUT, DELETE
                    http.requestMatchers(HttpMethod.POST, "/v1/category", "/v1/brand", "/v1/color", "/v1/product").authenticated();
                    http.requestMatchers(HttpMethod.PUT, "/v1/category", "/v1/brand", "/v1/color", "/v1/product").authenticated();
                    http.requestMatchers(HttpMethod.DELETE, "/v1/category", "/v1/brand", "/v1/color", "/v1/product").authenticated();
                            // Denegar acceso a cualquier otro endpoint
                    http.anyRequest().denyAll();
                })
                .build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailServiceImpl userDetailService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
