package br.com.tijuacu.gestaoalunos.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                // Como vamos usar JWT depois, deixamos stateless
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Para API REST com token, CSRF é desnecessário, então desabilitamos
                .csrf(AbstractHttpConfigurer::disable)

                // Desabilitar X-Frame-Options para permitir H2 Console
                .headers(headers -> headers
                        .frameOptions(HeadersConfigurer.FrameOptionsConfig::disable)
                )

                // Autorização das rotas
                .authorizeHttpRequests(auth -> auth
                        // Endpoints de Swagger e API docs liberados
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()
                        // Actuator (podemos restringir depois)
                        .requestMatchers(
                                "/actuator/health",
                                "/actuator/info"
                        ).permitAll()
                        // H2 Console
                        .requestMatchers(
                                "/h2-console/**"
                        ).permitAll()
                        // Por enquanto, liberamos tudo (depois trocaremos por .authenticated())
                        .anyRequest().permitAll()
                );

        return http.build();
    }
}