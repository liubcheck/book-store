package liubomyr.stepanenko.bookstore.repository.role;

import java.util.Optional;
import liubomyr.stepanenko.bookstore.model.Role;
import liubomyr.stepanenko.bookstore.model.type.RoleName;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(RoleName roleName);
}
