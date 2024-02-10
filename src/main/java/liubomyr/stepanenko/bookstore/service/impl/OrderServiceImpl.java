package liubomyr.stepanenko.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.math.BigDecimal;
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
import liubomyr.stepanenko.bookstore.exception.ShippingAddressException;
import liubomyr.stepanenko.bookstore.mapper.OrderItemMapper;
import liubomyr.stepanenko.bookstore.mapper.OrderMapper;
import liubomyr.stepanenko.bookstore.model.CartItem;
import liubomyr.stepanenko.bookstore.model.Order;
import liubomyr.stepanenko.bookstore.model.OrderItem;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.model.type.Status;
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
        String shippingAddress = orderRequstDto.getShippingAddress().isBlank()
                ? user.getShippingAddress()
                : orderRequstDto.getShippingAddress();
        if (shippingAddress == null || shippingAddress.isBlank()) {
            throw new ShippingAddressException("No shipping address has been provided");
        }
        if (user.getShippingAddress() == null || user.getShippingAddress().isBlank()) {
            user.setShippingAddress(shippingAddress);
            userRepository.save(user);
        }

        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_IdWithItems(user.getId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Couldn't find a shopping cart")
        );

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Status.COMPLETED);
        order.setTotal(getTotal(shoppingCart.getCartItems()));
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);

        Set<OrderItem> orderItems = orderItemsFromCartItems(shoppingCart.getCartItems(), order);
        order.setOrderItems(orderItems);

        orderRepository.save(order);

        shoppingCart.getCartItems().clear();
        shoppingCartRepository.save(shoppingCart);

        return orderMapper.toDto(order);
    }

    @Override
    public List<OrderDto> getAllOrders(Long userId) {
        return orderRepository.findAllByUser_IdWithItems(userId).stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional
    public OrderDto updateStatus(Long orderId, OrderStatusRequestDto statusRequestDto) {
        try {
            Status orderStatus = Status.valueOf(statusRequestDto.getStatus());
            Order orderForUpdate = orderRepository.findById(orderId).orElseThrow(
                    () -> new EntityNotFoundException("Couldn't find an order")
            );
            orderForUpdate.setStatus(orderStatus);
            return orderMapper.toDto(orderForUpdate);
        } catch (IllegalArgumentException ex) {
            throw new InvalidStatusException("Invalid order status");
        }
    }

    @Override
    public List<OrderItemDto> getAllItemsByOrder(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find an order by id = " + orderId)
        );
        return new ArrayList<>(order.getOrderItems().stream()
                .map(orderItemMapper::toDto)
                .toList());
    }

    @Override
    public OrderItemDto getOrderItemById(Long orderId, Long itemId) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find an order by id = " + orderId)
        );
        for (OrderItem orderItem : order.getOrderItems()) {
            if (orderItem.getId().equals(itemId)) {
                return orderItemMapper.toDto(orderItem);
            }
        }
        throw new EntityNotFoundException("Couldn't find an item by id = " + itemId
                + " within an order with id = " + orderId);
    }

    private BigDecimal getTotal(Set<CartItem> cartItems) {
        return cartItems.stream()
                .map(cartItem -> cartItem.getBook().getPrice()
                        .multiply(BigDecimal.valueOf(cartItem.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
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
}
