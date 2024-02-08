package liubomyr.stepanenko.bookstore.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Objects;
import java.util.Set;
import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemRequestDto;
import liubomyr.stepanenko.bookstore.dto.shoppingcart.ShoppingCartDto;
import liubomyr.stepanenko.bookstore.mapper.CartItemMapper;
import liubomyr.stepanenko.bookstore.mapper.ShoppingCartMapper;
import liubomyr.stepanenko.bookstore.model.CartItem;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.repository.cartitem.CartItemRepository;
import liubomyr.stepanenko.bookstore.repository.shoppingcart.ShoppingCartRepository;
import liubomyr.stepanenko.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ShoppingCartDto addItem(User user, CartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_IdWithItems(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a shopping cart"));
        Set<CartItem> cartItems = cartItemRepository.findByShoppingCart(shoppingCart);
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItems.add(cartItem);
        shoppingCart.setCartItems(cartItems);
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto get(User user) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_IdWithItems(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a shopping cart")
        );
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto updateItemQuantity(User user, Long cartItemId, Integer quantity) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_IdWithItems(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a shopping cart")
        );
        CartItem cartItemForUpdate = cartItemRepository.findById(cartItemId).orElseThrow(
                () -> new EntityNotFoundException("Couldn't find a cart item")
        );
        cartItemForUpdate.setQuantity(quantity);
        shoppingCart.getCartItems().add(cartItemForUpdate);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteItem(User user, Long cartItemId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUser_IdWithItems(user.getId())
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a shopping cart")
        );
        shoppingCart.getCartItems().removeIf(cartItem ->
                Objects.equals(cartItem.getId(), cartItemId));
        cartItemRepository.deleteById(cartItemId);
    }
}
