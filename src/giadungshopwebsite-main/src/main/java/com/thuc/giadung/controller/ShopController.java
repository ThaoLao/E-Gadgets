package com.thuc.giadung.controller;

import com.thuc.giadung.constant.SortType;
import com.thuc.giadung.dto.UserSearchDTO;
import com.thuc.giadung.service.ProductService;
import com.thuc.giadung.controller.common.BaseController;
import com.thuc.giadung.entity.Product;
import com.thuc.giadung.entity.Category;
import com.thuc.giadung.service.CategoryService;
import com.thuc.giadung.service.RecommendationService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@Controller
@RequestMapping("/shop")
public class ShopController extends BaseController {

    private CategoryService categoryService;
    private ProductService productService;
    private RecommendationService recommendationService;

    @GetMapping
    public String getShopPage(
            @ModelAttribute("searchModel") UserSearchDTO searchModel,
            @RequestParam(name = "page", defaultValue = "1") int page,
            Model model) {

        normalizeShopSearch(searchModel);

        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);

        int pageIndex = Math.max(page - 1, 0);
        Pageable pageable = PageRequest.of(pageIndex, 6);

        Page<Product> searchResult = productService.searchProductsUser(searchModel, pageable);

        model.addAttribute("products", searchResult);
        model.addAttribute("totalPages", searchResult.getTotalPages());
        model.addAttribute("currentPage", searchResult.getNumber());
        model.addAttribute("sortBy", searchModel.getSortBy());
        model.addAttribute("categoryId", searchModel.getCategoryId());
        model.addAttribute("keyword", searchModel.getKeyword());
        model.addAttribute("amountGap", searchModel.getAmountGap());

        if (searchModel.getCategoryId() != null) {
            Category category = categoryService.getCategoryById(searchModel.getCategoryId());
            model.addAttribute("categoryName", "Danh mục: " + category.getName());
        } else {
            model.addAttribute("categoryName", "Tất cả sản phẩm");
        }

        return "user/shop";
    }

    private void normalizeShopSearch(UserSearchDTO dto) {
        if (dto.getKeyword() == null) {
            dto.setKeyword("");
        } else {
            String k = dto.getKeyword().trim();
            if (k.length() > 500) {
                k = k.substring(0, 500);
            }
            dto.setKeyword(k);
        }
        if (dto.getSortBy() == null || dto.getSortBy().isBlank()) {
            dto.setSortBy(SortType.newest);
        }
        if (dto.getAmountGap() == null || dto.getAmountGap().isBlank()) {
            dto.setAmountGap("0 VND - 1.000.000 VND");
        }
    }

    @GetMapping("/product/{product_id}")
    public String viewProductDetail(@PathVariable Long product_id, Model model) {
        Product product = productService.getProductById(product_id);
        model.addAttribute("product", product);
        List<Product> similarProducts = recommendationService.getSimilarProducts(product_id, 4);
        model.addAttribute("similarProducts", similarProducts);
        return "user/product_details";
    }
}
