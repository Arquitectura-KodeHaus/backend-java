package com.kodehaus.demo.model;

import com.google.cloud.firestore.annotation.DocumentId;

import lombok.Data;

@Data
public class CatalogProduct {
    @DocumentId
    private String id;
    private String name;
    private String type;
    private double bulletinPrice;   
}
