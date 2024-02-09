package liubomyr.stepanenko.bookstore.repository.cartitem;

import java.util.Set;
import liubomyr.stepanenko.bookstore.model.CartItem;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
