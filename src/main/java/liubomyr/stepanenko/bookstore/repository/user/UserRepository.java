package liubomyr.stepanenko.bookstore.repository.user;

import java.util.Optional;
import liubomyr.stepanenko.bookstore.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
