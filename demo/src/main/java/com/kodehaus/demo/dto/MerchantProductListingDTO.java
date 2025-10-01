package com.kodehaus.demo.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantProductListingDTO {
    private String merchantProductId;
    private String merchantName;
    private String puestoName;
    private double price; // Precio del comerciante
    private double bulletinPrice; // Precio de boletín
    private Integer stock;
}