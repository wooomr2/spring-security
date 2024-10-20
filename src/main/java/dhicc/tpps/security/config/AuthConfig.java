package dhicc.tpps.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dhicc.tpps.security.TokenService;
import dhicc.tpps.repository.UserRepository;
import dhicc.tpps.security.CustomAuditAware;
import dhicc.tpps.security.JwtUtil;
import dhicc.tpps.security.filter.AuthProcessingFilter;
import dhicc.tpps.security.handler.LoginFailHandler;
import dhicc.tpps.security.handler.LoginSuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
//TODO::
//@EnableConfigurationProperties(JwtProperties.class)
@RequiredArgsConstructor
public class AuthConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    //  TODO:: Bean등록하든 뭘하든 삭제하느 방법을 찾자.
    private final JwtUtil jwtUtil;
    private final TokenService tokenService;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username + "을 찾을 수 없습니다."));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return new ProviderManager(authProvider);
    }

    @Bean
    public AuthProcessingFilter authProcessingFilter() {
        AuthProcessingFilter filter = new AuthProcessingFilter("/api/v1/auth/login", objectMapper);
        filter.setAuthenticationManager(authenticationManager());
        filter.setAuthenticationSuccessHandler(new LoginSuccessHandler(jwtUtil, tokenService, objectMapper));
        filter.setAuthenticationFailureHandler(new LoginFailHandler(objectMapper));

        return filter;
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new CustomAuditAware();
    }
}
