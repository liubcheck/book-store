package liubomyr.stepanenko.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotBlank
    private Long bookId;
    @Min(1)
    private Integer quantity;
}
