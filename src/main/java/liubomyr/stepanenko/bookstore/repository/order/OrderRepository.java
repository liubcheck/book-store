package liubomyr.stepanenko.bookstore.repository.order;

import java.util.List;
import liubomyr.stepanenko.bookstore.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("SELECT o FROM Order o JOIN FETCH o.orderItems WHERE o.user.id = :userId")
    List<Order> findAllByUser_IdWithItems(Long userId);
}
