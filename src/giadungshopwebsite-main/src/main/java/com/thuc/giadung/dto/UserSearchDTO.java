package com.thuc.giadung.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSearchDTO {
    private Long categoryId;
    private String sortBy;
    private String keyword;
    private String amountGap;

    public boolean isEmpty() {
        String k = keyword == null ? "" : keyword.trim();
        return categoryId == null && k.isEmpty() && (sortBy == null || sortBy.isEmpty());
    }
}
