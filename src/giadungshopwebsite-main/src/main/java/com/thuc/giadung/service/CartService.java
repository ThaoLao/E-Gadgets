package com.thuc.giadung.service;

import com.thuc.giadung.dto.CartItemDTO;
import com.thuc.giadung.dto.CartDTO;
import jakarta.servlet.http.HttpSession;

public interface CartService {
    void addToCart(HttpSession session, CartItemDTO cartItem);

    void updateCartItemQuantity(HttpSession session, Long productId, int quantity);

    void removeCartItem(HttpSession session, Long productId);

    void clearCart(HttpSession session);

    CartDTO getCart(HttpSession session);
}

