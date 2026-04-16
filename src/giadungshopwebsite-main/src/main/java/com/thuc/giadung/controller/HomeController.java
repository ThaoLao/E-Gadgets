package com.thuc.giadung.controller;

import com.thuc.giadung.controller.common.BaseController;
import com.thuc.giadung.entity.User;
import com.thuc.giadung.service.ProductService;
import com.thuc.giadung.entity.Product;
import com.thuc.giadung.service.CategoryService;
import com.thuc.giadung.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@AllArgsConstructor
@Controller

public class HomeController extends BaseController {

    private ProductService productService;
    private CategoryService categoryService;
    private RecommendationService recommendationService;

    @GetMapping("/")
    String getUserHomePage(Model model) {

        List<Product> top4BestSeller = productService.getTop4BestSeller();
        model.addAttribute("categories", categoryService.getAllCategories());
        model.addAttribute("top4BestSeller", top4BestSeller);
        List<Product> newProducts = productService.findAllOrderByCreatedDate();
        model.addAttribute("newProducts", newProducts);

        User currentUser = super.getCurrentUser();
        if (currentUser != null) {
            List<Product> recommendations = recommendationService.getRecommendationsForUser(currentUser.getId(), 8);
            model.addAttribute("recommendedProducts", recommendations);
        }

        return "user/index";
    }


}
