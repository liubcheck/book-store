package liubomyr.stepanenko.bookstore.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import liubomyr.stepanenko.bookstore.dto.orderitem.OrderItemDto;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    @JsonProperty("orderItems")
    private Set<OrderItemDto> orderItemDtos;
    private LocalDateTime orderDate;
    private BigDecimal total;
    private String status;
}
