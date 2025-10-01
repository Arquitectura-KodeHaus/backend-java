package com.kodehaus.demo.model;

import com.google.cloud.Timestamp;
import com.google.cloud.firestore.annotation.DocumentId;
import com.google.cloud.firestore.annotation.ServerTimestamp;

import lombok.Data;

@Data
public class MerchantProduct {
    @DocumentId
    private String id;
    private String idMerchant;
    private String idCatalogProduct;
    private double merchantPrice;
    @ServerTimestamp
    private Timestamp publishedAt;
    private boolean status;
}
