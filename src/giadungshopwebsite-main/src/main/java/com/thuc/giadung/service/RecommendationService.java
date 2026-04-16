package com.thuc.giadung.service;

import com.thuc.giadung.entity.Product;

import java.util.List;

public interface RecommendationService {

    List<Product> getRecommendationsForUser(Long userId, int limit);

    List<Product> getSimilarProducts(Long productId, int limit);

}
