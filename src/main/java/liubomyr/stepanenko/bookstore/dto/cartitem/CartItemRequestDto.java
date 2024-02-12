package liubomyr.stepanenko.bookstore.dto.cartitem;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class CartItemRequestDto {
    @NotBlank
    private Long bookId;
    @NotNull
    @Positive
    private Integer quantity;
}
