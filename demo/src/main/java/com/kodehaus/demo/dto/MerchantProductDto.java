package com.kodehaus.demo.dto;

import java.util.List;

import com.kodehaus.demo.model.MerchantProduct;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantProductDto {
    private List<MerchantProduct> merchantProducts;
}
