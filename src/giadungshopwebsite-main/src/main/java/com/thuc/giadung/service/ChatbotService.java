package com.thuc.giadung.service;

import com.thuc.giadung.dto.chat.ChatbotResponse;
import com.thuc.giadung.entity.Product;

import java.util.List;

public interface ChatbotService {
    ChatbotResponse processMessage(String message);

    List<Product> searchProductsByKeywords(List<String> keywords);
}
