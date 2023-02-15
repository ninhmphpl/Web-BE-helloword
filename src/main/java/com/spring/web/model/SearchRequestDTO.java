package com.spring.web.model;

import lombok.Data;

@Data
public class SearchRequestDTO {
    private String keyword;
    private Integer fromQuantity;
    private Integer toQuantity;
    private Double fromPrice;
    private Double toPrice;
}
