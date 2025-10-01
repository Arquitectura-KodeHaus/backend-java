package com.kodehaus.demo.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kodehaus.demo.dto.CatalogProductDto;
import com.kodehaus.demo.model.CatalogProduct;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.kodehaus.demo.utils.CatalogProductUtils.getCatalogProductsList;

@Service
@Slf4j
@AllArgsConstructor
public class CatalogProductService {
    private Firestore firestore;
    private final String COLLECTION_NAME = "catalogProducts";
    
    public String createCatalogProduct(CatalogProduct product) {
        try {
            ApiFuture<DocumentReference> catalogProducts = firestore.collection(COLLECTION_NAME).add(product);
            return "Document saved: catalogProduct iD is " + catalogProducts.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // READ
    public CatalogProduct getCatalogProduct(String id) {
        try {
            ApiFuture<DocumentSnapshot> catalogProducts = firestore.collection(COLLECTION_NAME).document(id).get();
            return catalogProducts.get().toObject(CatalogProduct.class);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public CatalogProductDto getAllCatalogProducts() {
        try {
            Query query = firestore.collection(COLLECTION_NAME);
            List<CatalogProduct> catalogProductList = getCatalogProductsList(query);
            return CatalogProductDto.builder().catalogProducts(catalogProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    public String updateCatalogProduct(Map<String, Object> catalogProduct) {
        try {
            if (catalogProduct.get("id") == null) {
            throw new RuntimeException("Product ID is required to update product");
            }
            String id = catalogProduct.get("id").toString();
            catalogProduct.remove("id");
            ApiFuture<WriteResult> catalogProducts = firestore.collection(COLLECTION_NAME)
                    .document(id)
                    .update(catalogProduct);
            return "catalogProduct updated at: " + catalogProducts.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public String deleteCatalogService(String id) {
        try {
            ApiFuture<WriteResult> catalogProducts = firestore.collection(COLLECTION_NAME).document(id).delete();
            return "catalogProducts deleted at : " + catalogProducts.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public CatalogProductDto findByType(String type) {
        if (type == null || type.isBlank()) {
            throw new RuntimeException("type is invalid!!");
        }

        try {
            // whereNotEqualTo
            Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("type", type);
            List<CatalogProduct> catalogProductList = getCatalogProductsList(query);
            return CatalogProductDto.builder().catalogProducts(catalogProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
