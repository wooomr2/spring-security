package dhicc.tpps.security.service;

import dhicc.tpps.security.mapper.UrlRoleMapper;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class DynamicAuthorizationService {
    private final UrlRoleMapper delegate;

    public Map<String, String> getUrlRolaMappings() {
        return delegate.getUrlRoleMappings();
    }
}