package liubomyr.stepanenko.bookstore.service.impl;

import jakarta.transaction.Transactional;
import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemRequestDto;
import liubomyr.stepanenko.bookstore.dto.shoppingcart.ShoppingCartDto;
import liubomyr.stepanenko.bookstore.exception.EntityNotFoundException;
import liubomyr.stepanenko.bookstore.mapper.CartItemMapper;
import liubomyr.stepanenko.bookstore.mapper.ShoppingCartMapper;
import liubomyr.stepanenko.bookstore.model.CartItem;
import liubomyr.stepanenko.bookstore.model.ShoppingCart;
import liubomyr.stepanenko.bookstore.repository.shoppingcart.ShoppingCartRepository;
import liubomyr.stepanenko.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    @Transactional
    public ShoppingCartDto addItem(Long userId, CartItemRequestDto cartItemRequestDto) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        CartItem cartItem = cartItemMapper.toModel(cartItemRequestDto);
        cartItem.setShoppingCart(shoppingCart);
        shoppingCart.getCartItems().add(cartItem);
        shoppingCartRepository.save(shoppingCart);
        return shoppingCartMapper.toDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto get(Long userId) {
        return shoppingCartMapper.toDto(getShoppingCart(userId));
    }

    @Override
    @Transactional
    public ShoppingCartDto updateItemQuantity(Long userId, Long cartItemId, Integer quantity) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        for (CartItem cartItem : shoppingCart.getCartItems()) {
            if (cartItem.getId().equals(cartItemId)) {
                cartItem.setQuantity(quantity);
                shoppingCartRepository.save(shoppingCart);
                return shoppingCartMapper.toDto(shoppingCart);
            }
        }
        throw new EntityNotFoundException("Couldn't find a cart item by id = " + cartItemId);
    }

    @Override
    @Transactional
    public void deleteItem(Long userId, Long cartItemId) {
        ShoppingCart shoppingCart = getShoppingCart(userId);
        shoppingCart.getCartItems().removeIf(cartItem ->
                cartItem.getId().equals(cartItemId));
    }

    private ShoppingCart getShoppingCart(Long userId) {
        return shoppingCartRepository.findByUserIdWithItems(userId)
                .orElseThrow(() -> new EntityNotFoundException("Couldn't find a shopping cart "
                        + "for user: " + userId));
    }
}
