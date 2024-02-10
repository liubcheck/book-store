package liubomyr.stepanenko.bookstore.repository.orderitem;

import java.util.List;
import liubomyr.stepanenko.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findAllByOrder_Id(Long orderId);
}
