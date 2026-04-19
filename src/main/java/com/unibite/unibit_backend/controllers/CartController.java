package com.unibite.unibit_backend.controllers;

import com.unibite.unibit_backend.dto.AddToCartRequest;
import com.unibite.unibit_backend.entity.CartItem;
import com.unibite.unibit_backend.service.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    /// ADD OR UPDATE QUANTITY
    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody @Valid AddToCartRequest request) {
        try {
            String email = getAuthenticatedUser();

            service.addToCart(email, request);

            return ResponseEntity.ok("Item added/updated in cart");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    /// GET CART
    @GetMapping
    public ResponseEntity<?> get() {
        try {
            String email = getAuthenticatedUser();

            List<CartItem> cart = service.getCart(email);

            return ResponseEntity.ok(cart);

        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    /// REMOVE ITEM
    @DeleteMapping("/remove/{id}")
    public ResponseEntity<?> remove(@PathVariable Long id) {
        try {
            String email = getAuthenticatedUser();

            service.removeFromCart(email, id);

            return ResponseEntity.ok("Item removed from cart");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    /// CLEAR CART
    @DeleteMapping("/clear")
    public ResponseEntity<?> clear() {
        try {
            String email = getAuthenticatedUser();

            service.clearCart(email);

            return ResponseEntity.ok("Cart cleared");

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Something went wrong");
        }
    }

    /// COMMON AUTH METHOD
    private String getAuthenticatedUser() {
        var auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !auth.isAuthenticated()) {
            throw new RuntimeException("Unauthorized");
        }

        return auth.getName();
    }
}