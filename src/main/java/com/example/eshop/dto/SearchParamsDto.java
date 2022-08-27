package com.example.eshop.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SearchParamsDto {
    private String searchKey;
    private String minPrice;
    private String maxPrice;
    private String categoryName;
}