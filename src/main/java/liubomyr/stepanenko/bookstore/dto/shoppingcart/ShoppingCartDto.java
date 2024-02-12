package liubomyr.stepanenko.bookstore.dto.shoppingcart;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Set;
import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemDto;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    @JsonProperty("cartItems")
    private Set<CartItemDto> cartItemDtos;
}
