package com.thuc.giadung.service.impl;

import com.thuc.giadung.dto.ProductDto;
import com.thuc.giadung.dto.UserSearchDTO;
import com.thuc.giadung.constant.SortType;
import com.thuc.giadung.dto.ProductSearchDTO;
import com.thuc.giadung.dto.MonthlyRevenueDTO;
import com.thuc.giadung.entity.*;
import com.thuc.giadung.repository.*;
import com.thuc.giadung.service.ProductService;
import com.thuc.giadung.service.CategoryService;
import com.thuc.giadung.service.FileUploadService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@RequiredArgsConstructor
@Service
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class ProductServiceImpl implements ProductService {

    ProductRepository productRepository;
    CategoryRepository categoryRepository;
    UserRepository userRepository;
    CategoryService categoryService;
    FileUploadService fileUploadService;
    OrderDetailRepository orderDetailRepository;
    PromotionRepository promotionRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllActive() {
        return productRepository.findAllByActiveFlag(true);
    }

    @Override
    public void addProduct(Product product, MultipartFile coverImage) throws IOException {
        Product savedProduct = productRepository.save(product);
        savedProduct.setCoverImage(fileUploadService.uploadFile(coverImage));
        productRepository.save(product);
    }

    @Override
    public void editProduct(Product product, MultipartFile coverImage) throws IOException {
        Product savedProduct = productRepository.save(product);
        if (!coverImage.isEmpty()) {
            savedProduct.setCoverImage(fileUploadService.uploadFile(coverImage));
            productRepository.save(product);
        }
    }

    @Override
    public void deleteProduct(Long id) throws Exception {
        List<OrderDetail> orderDetailsFindByProductId = orderDetailRepository.findByProductId((id));
        if(!orderDetailsFindByProductId.isEmpty()){
            throw new Exception("Đồ Điện Tử đã có trong các đơn hàng , vui lòng xoá các đơn hàng có đồ điện tử trước");
        }
        productRepository.deleteById(id);
    }

    @Override
    public void setActiveFlag(Long id, boolean activeFlag) throws Exception {
        Product productById = productRepository.findById(id).orElse(null);
        if(productById == null){
            throw new Exception("Không tìm thấy đồ điện tử với id này");
        }
        productById.setActiveFlag(activeFlag);
        productRepository.save(productById);
    }

    @Override
    public Product getProductById(Long id) {
        Optional<Product> productOptional = productRepository.findById(id);
        return productOptional.orElse(null);
    }

    @Override
    public Product getProductByName(String name) {
        return productRepository.findByTitleAndActiveFlag(name, true);
    }
    private String generateUniqueFileName(String originalFileName) {
        String timestamp = String.valueOf(System.currentTimeMillis());
        return timestamp + "_" + originalFileName;
    }


    @Override
    public Page<Product> searchProducts(ProductSearchDTO search, Pageable pageable) {
        Long categoryId = search.getCategoryId();
        String keyword = search.getKeyword();

        // Lấy dữ liệu phân trang dựa trên categoryId, keyword và các điều kiện tìm kiếm khác (nếu có)
        if (categoryId != null && keyword != null) {
            return productRepository.findByCategory_IdAndTitleContainingAndActiveFlag(categoryId, keyword, true, pageable);
        } else if (categoryId != null) {
            Category category = categoryRepository.findById(categoryId).orElse(null);
            return productRepository.findByCategoryAndActiveFlag(category, true, pageable);

        } else if (keyword != null) {
            return productRepository.findByTitleContainingAndActiveFlag(keyword, true, pageable);
        } else {
            return productRepository.findAll(pageable);
        }
    }

    @Override
    public Page<Product> searchProductsUser(UserSearchDTO search, Pageable pageable) {
        Long categoryId = search.getCategoryId();
        String keyword = search.getKeyword();
        if (keyword == null) keyword = "";
        String sortBy = search.getSortBy();
        String amountGap = search.getAmountGap();
        String[] temp = amountGap.split(" ");
        Double startAmount = Double.parseDouble(temp[0].replace(".", ""));
        Double endAmount = Double.parseDouble(temp[3].replace(".", ""));


        Page<Product> productsPage = productRepository.findAll(pageable);
        if (categoryId != null) {
            if (sortBy.equals(SortType.oldest)) {
                productsPage = productRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(categoryId, keyword, startAmount, endAmount, true, pageable);
            } else if (sortBy.equals(SortType.newest)) {
                productsPage = productRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(categoryId, keyword, startAmount, endAmount, true, pageable);

            } else if (sortBy.equals(SortType.priceLowToHigh)) {
                productsPage = productRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(categoryId, keyword, startAmount, endAmount, true, pageable);

            } else {
                productsPage = productRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(categoryId, keyword, startAmount, endAmount, true, pageable);
            }
        } else {
            if (sortBy.equals(SortType.oldest)) {
                productsPage = productRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtAsc(keyword, startAmount, endAmount, true, pageable);
            } else if (sortBy.equals(SortType.newest)) {
                productsPage = productRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(keyword, startAmount, endAmount, true, pageable);

            } else if (sortBy.equals(SortType.priceLowToHigh)) {
                productsPage = productRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceAsc(keyword, startAmount, endAmount, true, pageable);

            } else {
                productsPage = productRepository.findByTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderBySalePriceDesc(keyword, startAmount, endAmount, true, pageable);
            }
        }
        if (sortBy == null) {
            productsPage = productRepository.findByCategoryIdAndTitleContainingAndOriginalPriceBetweenAndActiveFlagOrderByCreatedAtDesc(categoryId, keyword, startAmount, endAmount, true, pageable);
        }
        return productsPage;
    }

    @Override
    public Page<Product> getAllProductsForUsers(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> getTop4BestSeller() {
        return productRepository.findTop4ByActiveFlagOrderByBuyCountDesc(true);
    }

    @Override
    public List<Product> getTop10BestSeller() {
        return productRepository.findTop10ByActiveFlagOrderByTotalRevenueDesc(true);
    }

    @Override
    public List<ProductDto> getTop10BestSellerByMonth(int month) {
        List<Object[]> result = productRepository.findTop10BestSellerByMonth(month);
        List<ProductDto> resultConvertedToDto = new ArrayList<>();
        for (Object[] item : result) {
            resultConvertedToDto.add(new ProductDto(item[0].toString(), Double.parseDouble(item[1].toString())));
        }
        return resultConvertedToDto;
    }

    @Override
    public List<Product> findAllOrderByCreatedDate() {
        return productRepository.findByActiveFlagOrderByCreatedAtDesc(true);
    }

    @Override
    public Set<Product> getFavoriteProductsByUserId(Long id) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            return user.getFavoriteProducts();
        }
        return Collections.emptySet();
    }

    @Override
    public Long countProduct() {
        return productRepository.count();
    }

    @Override
    public List<Product> getAllProductsByCategoryId(Long id) {
        Category category = categoryRepository.findById(id).orElse(null);
        return productRepository.findAllByCategoryAndActiveFlag(category, true);
    }

    @Override
    public Page<Product> getAllProductsPaginatedByCategoryId(Long categoryId, Pageable pageable) {
        return productRepository.findByCategoryIdAndActiveFlag(categoryId, true, pageable);
    }

    @Override
    public List<MonthlyRevenueDTO> getMonthRevenuePerYear(int year) {
        List<Object[]> result = productRepository.findMonthlyRevenue(year);
        List<MonthlyRevenueDTO> resultConvertedToDto = new ArrayList<>();
        for (Object[] item : result) {
            resultConvertedToDto.add(new MonthlyRevenueDTO(Integer.parseInt(item[0].toString()), Double.parseDouble(item[1].toString())));
        }
        return resultConvertedToDto;
    }

    @Override
    public void addProductToPromotion(Long productId){
        Promotion promotion = promotionRepository.findClosestActivePromotion();
        if(promotion == null) {
            throw new IllegalStateException("Hiện tại không có chương trình khuyến mãi no.");
        }
        Product product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            product.setPromotion(promotion);
            product.setSalePrice(product.getOriginalPrice() * ((100 - promotion.getDiscountPercentage()) / 100));
            productRepository.save(product);
        }
    }
}
