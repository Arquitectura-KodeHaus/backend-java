package com.kodehaus.demo.dto;

import java.util.List;

import com.kodehaus.demo.model.CatalogProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogProductDto {
    private List<CatalogProduct> catalogProducts;
}
