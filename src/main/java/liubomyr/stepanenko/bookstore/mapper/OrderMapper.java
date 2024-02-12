package liubomyr.stepanenko.bookstore.mapper;

import java.math.BigDecimal;
import java.util.Set;
import liubomyr.stepanenko.bookstore.config.MapperConfig;
import liubomyr.stepanenko.bookstore.dto.order.OrderDto;
import liubomyr.stepanenko.bookstore.model.Order;
import liubomyr.stepanenko.bookstore.model.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "orderItemDtos", source = "orderItems")
    OrderDto toDto(Order order);

    @AfterMapping
    default void setTotalPrice(@MappingTarget OrderDto dto, Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : orderItems) {
            BigDecimal orderItemSum = orderItem.getPrice()
                    .multiply(BigDecimal.valueOf(orderItem.getQuantity()));
            totalPrice = totalPrice.add(orderItemSum);
        }
        dto.setTotal(totalPrice);
    }
}
