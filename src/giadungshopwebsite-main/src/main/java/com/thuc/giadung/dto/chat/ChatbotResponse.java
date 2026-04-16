package com.thuc.giadung.dto.chat;

import com.thuc.giadung.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatbotResponse {
    private String message;
    private List<Product> recommendedProducts;
}
