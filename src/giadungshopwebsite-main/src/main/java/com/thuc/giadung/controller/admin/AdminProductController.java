package com.thuc.giadung.controller.admin;

import com.thuc.giadung.controller.common.BaseController;
import com.thuc.giadung.dto.ProductSearchDTO;
import com.thuc.giadung.entity.Product;
import com.thuc.giadung.entity.Category;
import com.thuc.giadung.service.ProductService;
import com.thuc.giadung.service.CategoryService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@AllArgsConstructor
@Controller
@RequestMapping("/admin/products_management")
public class AdminProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;


    @GetMapping
    public String showProductsPage(Model model,
                                @RequestParam(name = "page", defaultValue = "1") int page,
                                @RequestParam(name = "size", defaultValue = "4") int size,
                                @ModelAttribute("search") ProductSearchDTO search) {
        Page<Product> productPage = productService.searchProducts(search, PageRequest.of(page - 1, size));
        List<Category> categories = categoryService.getAllCategories();

        model.addAttribute("productPage", productPage);
        model.addAttribute("categories", categories);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());

        return "admin/products";
    }


    @GetMapping("/add")
    public String showAddProductForm(Model model) {
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("categories", categories);
        model.addAttribute("product", new Product());
        return "admin/products_add_or_update";
    }

    @PostMapping("/add")
    public String addOrUpdateProduct(@ModelAttribute("product") @Valid Product product,
                                  BindingResult bindingResult,
                                  @RequestParam("cover_image") MultipartFile coverImage,
                                  @RequestParam(value = "extraDataKeys", required = false) List<String> extraDataKeys,
                                  @RequestParam(value = "extraDataValues", required = false) List<String> extraDataValues,
                                  Model model
            , RedirectAttributes redirectAttributes) throws IOException {

        if (bindingResult.hasErrors()) {
            List<Category> categories = categoryService.getAllCategories();
            model.addAttribute("categories", categories);
            model.addAttribute("error", "Đã có lỗi xảy ra vui lòng nhập lại");
            return "admin/products_add_or_update";
        }

        // Process extra data
        if (extraDataKeys != null && extraDataValues != null && !extraDataKeys.isEmpty()) {
            Map<String, Object> extraDataMap = new HashMap<>();
            for (int i = 0; i < extraDataKeys.size() && i < extraDataValues.size(); i++) {
                String key = extraDataKeys.get(i).trim();
                String value = extraDataValues.get(i).trim();
                if (!key.isEmpty() && !value.isEmpty()) {
                    extraDataMap.put(key, value);
                }
            }
            product.setExtraData(extraDataMap);
        }


        if (product.getId() != null) {
            // Check if there is an existing product with the given ID
            Product existingProduct = productService.getProductById(product.getId());
            if (existingProduct != null) {
                // Update the product with new data
                if (product.getPublishedDate() == null) {
                    product.setPublishedDate(existingProduct.getPublishedDate());
                }
                if (coverImage.isEmpty()) {
                    product.setCoverImage(existingProduct.getCoverImage());
                }
                // Keep existing extra data if no new data provided
                if (product.getExtraData() == null || product.getExtraData().isEmpty()) {
                    product.setExtraData(existingProduct.getExtraData());
                }

                productService.editProduct(product, coverImage);
                Product editedProduct = productService.getProductById(product.getId());
                model.addAttribute("product", editedProduct);
                redirectAttributes.addFlashAttribute("message", "Sửa thông tin đồ điện tử thành công!");
            }
        } else {
            Product exist = productService.getProductByName(product.getTitle());

            if (exist != null) {
                model.addAttribute("error", "Đã tồn tại đồ điện tử với tên này");
                return "admin/products_add_or_update";
            } else productService.addProduct(product, coverImage);   
            redirectAttributes.addFlashAttribute("message", "Thêm đồ điện tử thành công!");
        }

        return "redirect:/admin/products_management/add";
    }


    @GetMapping("/edit/{id}")
    public String showEditProductForm(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id);
        List<Category> categories = categoryService.getAllCategories();
        model.addAttribute("product", product);
        model.addAttribute("categories", categories);

        return "admin/products_add_or_update";
    }


    @GetMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id, @RequestParam(defaultValue = "false") boolean activeFlag, RedirectAttributes redirectAttributes) {
        try{
            productService.setActiveFlag(id, activeFlag);
            // Add a success message to the model
            String action = activeFlag ? "kích hoạt lại trạng thái đang bán cho" : "cập nhật trạng thái không bán cho";
            redirectAttributes.addFlashAttribute("message", action + " đồ điện tử thành công!");

            return "redirect:/admin/products_management";
        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/products_management";
        }


    }

    @GetMapping("/promotion/{id}")
    public String addProductToPromotion(@PathVariable String id, RedirectAttributes redirectAttributes) {
        try{
            productService.addProductToPromotion(Long.parseLong(id));
            // Add a success message to the model
            redirectAttributes.addFlashAttribute("message",  "Thêm sản phẩm vào khuyến mãi thành công!");
            return "redirect:/admin/products_management";
        } catch (Exception ex){
            redirectAttributes.addFlashAttribute("message", ex.getMessage());
            return "redirect:/admin/products_management";
        }
    }


}
