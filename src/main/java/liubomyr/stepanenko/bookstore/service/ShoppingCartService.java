package liubomyr.stepanenko.bookstore.service;

import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemRequestDto;
import liubomyr.stepanenko.bookstore.dto.shoppingcart.ShoppingCartDto;
import liubomyr.stepanenko.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartDto addItem(User user, CartItemRequestDto cartItemRequestDto);

    ShoppingCartDto get(User user);

    ShoppingCartDto updateItemQuantity(User user, Long cartItemId, Integer quantity);

    void deleteItem(User user, Long cartItemId);
}
