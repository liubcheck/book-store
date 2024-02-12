package liubomyr.stepanenko.bookstore.repository.shoppingcart;

import java.util.Optional;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("SELECT sc FROM ShoppingCart sc LEFT JOIN FETCH sc.cartItems WHERE sc.user.id = :userId")
    Optional<ShoppingCart> findByUserIdWithItems(@Param("userId") Long userId);
}
