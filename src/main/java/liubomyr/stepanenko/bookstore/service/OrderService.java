package liubomyr.stepanenko.bookstore.service;

import java.util.List;
import liubomyr.stepanenko.bookstore.dto.order.OrderDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderRequstDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderStatusRequestDto;
import liubomyr.stepanenko.bookstore.dto.orderitem.OrderItemDto;
import liubomyr.stepanenko.bookstore.model.User;

public interface OrderService {
    OrderDto makeOrder(User user, OrderRequstDto orderRequstDto);

    List<OrderDto> getAllOrders(Long userId);

    OrderDto updateStatus(Long orderId, OrderStatusRequestDto statusRequestDto);

    List<OrderItemDto> getAllItemsByOrder(Long orderId);

    OrderItemDto getOrderItemById(Long orderId, Long itemId);
}
