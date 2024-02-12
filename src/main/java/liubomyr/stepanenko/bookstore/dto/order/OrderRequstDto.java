package liubomyr.stepanenko.bookstore.dto.order;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OrderRequstDto {
    @NotBlank
    private String shippingAddress;
}
