package dhicc.tpps.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dhicc.tpps.repository.UserRepository;
import dhicc.tpps.security.CustomAuditAware;
import dhicc.tpps.security.filter.AuthProcessingFilter;
import dhicc.tpps.security.handler.LoginFailHandler;
import dhicc.tpps.security.handler.LoginSuccessHandler;
import dhicc.tpps.security.service.RoleHierarchyService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class AuthConfig {

    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;
    private final LoginSuccessHandler loginSuccessHandler;
    private final LoginFailHandler loginFailHandler;

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
        filter.setAuthenticationSuccessHandler(loginSuccessHandler);
        filter.setAuthenticationFailureHandler(loginFailHandler);

        return filter;
    }

    @Bean
    public AuditorAware<Long> auditorAware() {
        return new CustomAuditAware();
    }

    @Bean
    public RoleHierarchyImpl roleHierarchy(RoleHierarchyService roleHierarchyService) {

        String allHierarchy = roleHierarchyService.findAllHierarchy();
        RoleHierarchyImpl roleHierarchy = RoleHierarchyImpl.fromHierarchy(allHierarchy);

        return roleHierarchy;
    }
}
