package liubomyr.stepanenko.bookstore.service;

import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemRequestDto;
import liubomyr.stepanenko.bookstore.dto.shoppingcart.ShoppingCartDto;

public interface ShoppingCartService {
    ShoppingCartDto addItem(Long userId, CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto get(Long userId);

    ShoppingCartDto updateItemQuantity(Long userId, Long cartItemId, Integer quantity);

    void deleteItem(Long userId, Long cartItemId);
}
