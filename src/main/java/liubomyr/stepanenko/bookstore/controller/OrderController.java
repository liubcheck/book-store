package liubomyr.stepanenko.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import liubomyr.stepanenko.bookstore.dto.order.OrderDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderRequstDto;
import liubomyr.stepanenko.bookstore.dto.order.OrderStatusRequestDto;
import liubomyr.stepanenko.bookstore.dto.orderitem.OrderItemDto;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Orders management",
        description = "Endpoints for managing orders")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Creates a new order from the shopping cart",
            description = "Saves a new order and order items to the database")
    public OrderDto makeOrder(Authentication authentication,
                              @RequestBody OrderRequstDto orderRequstDto) {
        User user = (User) authentication.getPrincipal();
        return orderService.makeOrder(user, orderRequstDto);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Gets the user's order history",
            description = "Gets all orders assigned to the user")
    public List<OrderDto> getOrdersHistory(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return orderService.getAllOrders(user.getId());
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Changes the order status",
            description = "Changes the order status by order ID")
    public OrderDto updateOrderStatus(@PathVariable Long id,
                                      @RequestBody OrderStatusRequestDto statusRequestDto) {
        return orderService.updateStatus(id, statusRequestDto);
    }

    @GetMapping("/{orderId}/items")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Gets all items of specific order",
            description = "Gets all items assigned to specific order in the database")
    public List<OrderItemDto> getAllItemsByOrder(@PathVariable Long orderId) {
        return orderService.getAllItemsByOrder(orderId);
    }

    @GetMapping("/{orderId}/items/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Gets any item from specific order",
            description = "Gets any specific item by its ID")
    public OrderItemDto getOrderItemById(@PathVariable Long orderId,
                                         @PathVariable Long itemId) {
        return orderService.getOrderItemById(orderId, itemId);
    }
}
