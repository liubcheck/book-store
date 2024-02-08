package liubomyr.stepanenko.bookstore.dto.shoppingcart;

import java.util.Set;
import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemDto;
import lombok.Data;

@Data
public class ShoppingCartDto {
    private Long id;
    private Long userId;
    private Set<CartItemDto> cartItemDtos;
}
