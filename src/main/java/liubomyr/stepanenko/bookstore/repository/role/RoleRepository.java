package liubomyr.stepanenko.bookstore.repository.role;

import liubomyr.stepanenko.bookstore.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
