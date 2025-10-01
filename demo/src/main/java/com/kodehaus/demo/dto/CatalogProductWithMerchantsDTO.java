package com.kodehaus.demo.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class CatalogProductWithMerchantsDTO {
    private String id;
    private String name;
    private String type;
    private int merchantCount;
    private List<MerchantProductListingDTO> merchants;
}