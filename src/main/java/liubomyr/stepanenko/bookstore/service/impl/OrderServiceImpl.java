package liubomyr.stepanenko.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import liubomyr.stepanenko.bookstore.dto.order.OrderDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderRequstDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderStatusRequestDto;
import liubomyr.stepanenko.bookstore.dto.orderitem.OrderItemDto;
import liubomyr.stepanenko.bookstore.exception.EmptyShoppingCartException;
import liubomyr.stepanenko.bookstore.exception.InvalidStatusException;
import liubomyr.stepanenko.bookstore.mapper.OrderItemMapper;
import liubomyr.stepanenko.bookstore.mapper.OrderMapper;
import liubomyr.stepanenko.bookstore.model.CartItem;
import liubomyr.stepanenko.bookstore.model.Order;
import liubomyr.stepanenko.bookstore.model.OrderItem;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.model.type.OrderStatus;
import liubomyr.stepanenko.bookstore.repository.order.OrderRepository;
import liubomyr.stepanenko.bookstore.repository.shoppingcart.ShoppingCartRepository;
import liubomyr.stepanenko.bookstore.repository.user.UserRepository;
import liubomyr.stepanenko.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderDto makeOrder(User user, OrderRequstDto orderRequstDto) {
        if (user.getShippingAddress() == null || user.getShippingAddress().isBlank()) {
            user.setShippingAddress(orderRequstDto.getShippingAddress());
            userRepository.save(user);
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserIdWithItems(user.getId())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Couldn't find a shopping cart for user: " + user.getId())
        );

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.COMPLETED);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(orderRequstDto.getShippingAddress());

        Set<OrderItem> orderItems = orderItemsFromCartItems(shoppingCart.getCartItems(), order);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId) {
        return orderRepository.findAllByUserIdWithItems(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto updateStatus(Long orderId, OrderStatusRequestDto statusRequestDto) {
        try {
            OrderStatus orderStatus = OrderStatus.valueOf(statusRequestDto.getStatus());
            Order orderForUpdate = getOrder(orderId);
            orderForUpdate.setStatus(orderStatus);
            return orderMapper.toDto(orderForUpdate);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException("Invalid order status");
        }
    }

    @Override
    public List<OrderItemDto> getAllItemsByOrder(Long orderId) {
        Order order = getOrder(orderId);
        return new ArrayList<>(order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList());
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        Order order = getOrder(orderId);
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getId().equals(itemId)) {
                return orderItemMapper.toDto(orderItem);
            }
        }
        throw new EntityNotFoundException("Couldn't find an item by id = " + itemId
                + " within an order with id = " + orderId);
    }

    private Set<OrderItem> orderItemsFromCartItems(Set<CartItem> cartItems, Order order) {
        if (cartItems.isEmpty()) {
            throw new EmptyShoppingCartException("No order with empty shopping cart");
        }
        return cartItems.stream()
                .map(orderItemMapper::toOrderItemFromCartItem)
                .peek(orderItem -> orderItem.setOrder(order))
                .collect(Collectors.toSet());
    }

    private Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find an order by id = " + orderId)
        );
    }
}
