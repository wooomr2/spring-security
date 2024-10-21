package dhicc.tpps.security.service;

import dhicc.tpps.entity.RoleHierarchy;
import dhicc.tpps.repository.RoleHierarchyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleHierarchyService {

    private final RoleHierarchyRepository roleHierarchyRepository;

    public String findAllHierarchy() {
        List<RoleHierarchy> roleHierarchyList = roleHierarchyRepository.findAll();

        Iterator<RoleHierarchy> iterator = roleHierarchyList.iterator();
        StringBuilder hierarchyRole = new StringBuilder();

        while (iterator.hasNext()) {
            RoleHierarchy roleHierarchy = iterator.next();
            if (roleHierarchy.getParent() != null) {
                hierarchyRole.append(roleHierarchy.getParent().getRoleName());
                hierarchyRole.append(" >");
                hierarchyRole.append(roleHierarchy.getRoleName());
                hierarchyRole.append("\n");
            }
        }

        return hierarchyRole.toString();
    }
}
