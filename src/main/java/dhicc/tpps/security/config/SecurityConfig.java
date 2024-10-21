package dhicc.tpps.security.config;

import dhicc.tpps.entity.ENUM_ROLE;
import dhicc.tpps.security.filter.AuthProcessingFilter;
import dhicc.tpps.security.filter.JwtAuthenticationFilter;
import dhicc.tpps.security.filter.JwtExceptionFilter;
import dhicc.tpps.security.handler.CustomLogoutHandler;
import dhicc.tpps.security.handler.Http401Handler;
import dhicc.tpps.security.handler.Http403Handler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final Http401Handler http401Handler;
    private final Http403Handler http403Handler;
    private final AuthProcessingFilter authProcessingFilter;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final JwtExceptionFilter jwtExceptionFilter;
    private final CustomLogoutHandler customLogoutHandler;

    private static final String[] WHITE_LIST_URL = {
            "/api/v1/auth/**",
            "/api/v1/auth/signup",
            "/v1/api-docs/**",
            "/swagger-resources/**",
            "/swagger-ui/**",
    };

    private static final String[] ALLOWED_ORIGINS = {
            "http://localhost:3000",
            "http://localhost:8080",
            "http://localhost:8081",
            "http://localhost:80",
            "*"
    };

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of(ALLOWED_ORIGINS));
        configuration.setAllowedMethods(List.of("*"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(v -> v.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers("/api/v1/admin/**").hasRole(ENUM_ROLE.ADMIN.name())
                                .anyRequest()
                                .authenticated()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .exceptionHandling(e -> e
                        .authenticationEntryPoint(http401Handler)
                        .accessDeniedHandler(http403Handler)
                )
                .addFilterBefore(authProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
                .logout(logout -> logout
                        .logoutUrl("/api/v1/auth/logout")
                        .addLogoutHandler(customLogoutHandler)
                        .logoutSuccessHandler((request, response, authentication) -> {
                            SecurityContextHolder.clearContext();
                        })
                );

        return http.build();
    }
}
