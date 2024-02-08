package liubomyr.stepanenko.bookstore.controller;

import jakarta.validation.constraints.Positive;
import liubomyr.stepanenko.bookstore.dto.cartitem.CartItemRequestDto;
import liubomyr.stepanenko.bookstore.dto.shoppingcart.ShoppingCartDto;
import liubomyr.stepanenko.bookstore.model.User;
import liubomyr.stepanenko.bookstore.service.ShoppingCartService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("hasRole('ROLE_USER')")
@RequiredArgsConstructor
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @PostMapping
    public ShoppingCartDto addItem(Authentication authentication,
                                   @RequestBody CartItemRequestDto cartItemRequestDto) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.addItem(user, cartItemRequestDto);
    }

    @GetMapping
    public ShoppingCartDto getShoppingCart(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.get(user);
    }

    @PutMapping("/{cartItemId}")
    public ShoppingCartDto updateCartItemInShoppingCart(Authentication authentication,
                                                        @PathVariable Long cartItemId,
                                                        @RequestParam @Positive Integer quantity) {
        User user = (User) authentication.getPrincipal();
        return shoppingCartService.updateItemQuantity(user, cartItemId, quantity);
    }

    @DeleteMapping("/{cartItemId}")
    public String removeCartItemFromShoppingCart(Authentication authentication,
                                                        @PathVariable Long cartItemId) {
        User user = (User) authentication.getPrincipal();
        shoppingCartService.deleteItem(user, cartItemId);
        return "Cart item with id = " + cartItemId + " was successfully deleted";
    }
}
