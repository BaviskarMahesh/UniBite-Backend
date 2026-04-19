package com.unibite.unibit_backend.service;

import com.unibite.unibit_backend.dto.AddToCartRequest;
import com.unibite.unibit_backend.entity.Cart;
import com.unibite.unibit_backend.entity.CartItem;
import com.unibite.unibit_backend.entity.FoodItem;
import com.unibite.unibit_backend.repository.CartItemRepository;
import com.unibite.unibit_backend.repository.CartRepository;
import com.unibite.unibit_backend.repository.FoodItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;


    @Transactional
    public void addToCart(String email, AddToCartRequest request) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseGet(() -> cartRepository.save(
                        Cart.builder().userEmail(email).build()
                ));

        FoodItem foodItem = foodItemRepository.findById(request.getFoodId())
                .orElseThrow(() -> new RuntimeException("Food item not found"));

        /// CHECK IF ITEM ALREADY EXISTS
        CartItem existingItem = cartItemRepository
                .findByCartAndFoodItem(cart, foodItem)
                .orElse(null);

        if (existingItem != null) {

            int newQty = existingItem.getQuantity() + request.getQuantity();

            /// REMOVE IF QUANTITY <= 0
            if (newQty <= 0) {
                cartItemRepository.delete(existingItem);
            } else {
                existingItem.setQuantity(newQty);
                cartItemRepository.save(existingItem); // ensure update
            }

        } else {

            /// ONLY ADD IF QUANTITY > 0
            if (request.getQuantity() > 0) {
                CartItem newItem = CartItem.builder()
                        .cart(cart)
                        .foodItem(foodItem)
                        .quantity(request.getQuantity())
                        .build();

                cartItemRepository.save(newItem);
            }
        }
    }

    /// GET CART ITEMS
    public List<CartItem> getCart(String email) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return cartItemRepository.findByCart(cart);
    }

    /// REMOVE SINGLE ITEM
    @Transactional
    public void removeFromCart(String email, Long cartItemId) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        if (!item.getCart().getId().equals(cart.getId())) {
            throw new RuntimeException("Unauthorized access to cart item");
        }

        cartItemRepository.delete(item);
    }

    /// CLEAR FULL CART
    @Transactional
    public void clearCart(String email) {

        Cart cart = cartRepository.findByUserEmail(email)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        List<CartItem> items = cartItemRepository.findByCart(cart);

        cartItemRepository.deleteAll(items);
    }
}