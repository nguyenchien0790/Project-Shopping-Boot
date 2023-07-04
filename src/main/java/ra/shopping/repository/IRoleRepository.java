package ra.shopping.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ra.shopping.model.RoleName;
import ra.shopping.model.Roles;
import java.util.Optional;

public interface IRoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(RoleName roleName);
}
