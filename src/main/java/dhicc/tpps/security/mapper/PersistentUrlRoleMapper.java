package dhicc.tpps.security.mapper;


import dhicc.tpps.entity.Resource;
import dhicc.tpps.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PersistentUrlRoleMapper implements UrlRoleMapper {

    private final LinkedHashMap<String, String> urlRoleMappings = new LinkedHashMap<>();

    private final ResourceRepository resourceRepository;

    @Override
    public Map<String, String> getUrlRoleMappings() {

        List<Resource> resourceList = resourceRepository.findAllResources();
        resourceList.forEach(resource -> {
            resource.getRoleSet().forEach(role -> {
                urlRoleMappings.put(resource.getResourceName(), role.getRoleName());
            });
        });

        return urlRoleMappings;
    }
}