package liubomyr.stepanenko.bookstore.dto.order;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequstDto {
    @NotNull
    private String shippingAddress;
}
