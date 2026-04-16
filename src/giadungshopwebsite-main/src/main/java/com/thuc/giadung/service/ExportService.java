package com.thuc.giadung.service;

import com.thuc.giadung.dto.ProductDto;
import com.thuc.giadung.entity.User;
import com.thuc.giadung.dto.CategoryDto;
import com.thuc.giadung.dto.OrderDTO;

import java.util.List;

public interface ExportService {

    String exportOrderReport(User user, List<OrderDTO> orderDTOList, String keyword);

    String exportCategoryReport(User user, List<CategoryDto> categoryDTOList, String keyword);

    String exportProductReport(User user, List<ProductDto> productDtoList, String keyword);

}
