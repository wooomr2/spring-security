package dhicc.tpps.security.manager;

import dhicc.tpps.repository.ResourceRepository;
import dhicc.tpps.security.mapper.PersistentUrlRoleMapper;
import dhicc.tpps.security.service.DynamicAuthorizationService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcherEntry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomDynamicAuthorizationManager implements AuthorizationManager<RequestAuthorizationContext> {

    List<RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>>> mappings;

    // 구현
    private final ResourceRepository resourceRepository;
    private DynamicAuthorizationService dynamicAuthorizationService;

    private final HandlerMappingIntrospector handlerMappingIntrospector;
    private final RoleHierarchyImpl roleHierarchy;


    @PostConstruct
    public void mapping() {
        dynamicAuthorizationService = new DynamicAuthorizationService(new PersistentUrlRoleMapper(resourceRepository));

//        TODO:: MvcRequestMathcer 말고 어떤 걸 써야 할 까.....
        mappings = dynamicAuthorizationService.getUrlRolaMappings().entrySet().stream().map(
                entry -> new RequestMatcherEntry<>(
                        new MvcRequestMatcher(handlerMappingIntrospector, entry.getKey()),
                        customAuthorizationManager(entry.getValue()
                        )
                )
        ).collect(Collectors.toList());
    }

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {

        for (RequestMatcherEntry<AuthorizationManager<RequestAuthorizationContext>> mapping : this.mappings) {
            RequestMatcher matcher = mapping.getRequestMatcher();

        }
    }

    public synchronized void reload() {
        this.mappings = dynamicAuthorizationService.getUrlRolaMappings().entrySet().stream().map(
                entry -> new RequestMatcherEntry<>(
                        new AntPathRequestMatcher(entry.getKey()),
                        customAuthorizationManager(entry.getValue())
                )
        ).collect(Collectors.toList());
    }

    private AuthorizationManager<RequestAuthorizationContext> customAuthorizationManager(String role) {
        if (role.startsWith("ROLE")) {
            AuthorityAuthorizationManager<RequestAuthorizationContext> authorizationManager = AuthorityAuthorizationManager.hasRole(role);
            authorizationManager.setRoleHierarchy(roleHierarchy);

            return authorizationManager;
        } else {
            DefaultHttpSecurityExpressionHandler handler = new DefaultHttpSecurityExpressionHandler();
            handler.setRoleHierarchy(roleHierarchy);

            WebExpressionAuthorizationManager authorizationManager = new WebExpressionAuthorizationManager(role);
            authorizationManager.setExpressionHandler(handler);

            return authorizationManager;
        }
    }

}
