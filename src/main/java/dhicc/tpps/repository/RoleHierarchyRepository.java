package dhicc.tpps.repository;

import dhicc.tpps.entity.RoleHierarchy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleHierarchyRepository extends JpaRepository<RoleHierarchy, Long> {
}
