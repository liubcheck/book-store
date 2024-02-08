package liubomyr.stepanenko.bookstore.dto.cartitem;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotBlank
    private Long bookId;
    @NotBlank
    private Integer quantity;
}
