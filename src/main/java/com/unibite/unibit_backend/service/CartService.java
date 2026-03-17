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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final FoodItemRepository foodItemRepository;
    public void addToCart(String email, AddToCartRequest request){
        Cart cart=cartRepository.findByUserEmail(email)
                .orElseGet(()->cartRepository.save(Cart.builder().userEmail(email).build()));

        FoodItem foodItem=foodItemRepository.findById(request.getFoodId())
                .orElseThrow();
        cartItemRepository.save(
                CartItem.builder()
                        .cart(cart)
                        .foodItem(foodItem)
                        .quantity(request.getQuantity())
                        .build()
        );

    }
    public List<CartItem> getCart(String email){
        Cart cart =cartRepository.findByUserEmail(email).orElseThrow();
        return cartItemRepository.findByCart(cart);
    }
}
