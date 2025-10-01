package com.kodehaus.demo.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kodehaus.demo.dto.MerchantProductDto;
import com.kodehaus.demo.model.MerchantProduct;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static com.kodehaus.demo.utils.MerchantProductUtils.getMerchantProductsList;

@Service
@Slf4j
@AllArgsConstructor
public class MerchantProductService {
    private Firestore firestore;
    private final String COLLECTION_NAME = "merchantProducts";
    public String createMerchantProduct(MerchantProduct product) {
        try {
            ApiFuture<DocumentReference> merchantProducts = firestore.collection(COLLECTION_NAME).add(product);
            return "Document saved: merchantProduct iD is " + merchantProducts.get().getId();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // READ
    public MerchantProduct getMerchantProduct(String id) {
        try {
            ApiFuture<DocumentSnapshot> merchantProducts = firestore.collection(COLLECTION_NAME).document(id).get();
            return merchantProducts.get().toObject(MerchantProduct.class);
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MerchantProductDto getAllMerchantProducts() {
        try {
            Query query = firestore.collection(COLLECTION_NAME);
            List<MerchantProduct> merchantProductList = getMerchantProductsList(query);
            return MerchantProductDto.builder().merchantProducts(merchantProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    // UPDATE
    public String updateMerchantProduct(Map<String, Object> merchantProduct) {
        try {
            if (merchantProduct.get("id") == null) {
            throw new RuntimeException("Product ID is required to update product");
            }
            String id = merchantProduct.get("id").toString();
            merchantProduct.remove("id");
            ApiFuture<WriteResult> merchantProducts = firestore.collection(COLLECTION_NAME)
                    .document(id)
                    .update(merchantProduct);
            return "merchantProduct updated at: " + merchantProducts.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    //DELETE
    public String deleteMerchantService(String id) {
        try {
            ApiFuture<WriteResult> merchantProducts = firestore.collection(COLLECTION_NAME).document(id).delete();
            return "merchantProducts deleted at : " + merchantProducts.get().getUpdateTime();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MerchantProductDto findActive() {
        try {
            // whereNotEqualTo
            Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("status", true);
            List<MerchantProduct> merchantProductList = getMerchantProductsList(query);
            return MerchantProductDto.builder().merchantProducts(merchantProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MerchantProductDto findActiveByMerchant(String idMerchant) {
        try {
            // whereNotEqualTo
            Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("status", true).whereEqualTo("idMerchant", idMerchant);
            List<MerchantProduct> merchantProductList = getMerchantProductsList(query);
            return MerchantProductDto.builder().merchantProducts(merchantProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MerchantProductDto findByMerchant(String idMerchant) {
        try {
            // whereNotEqualTo
            Query query = firestore.collection(COLLECTION_NAME).whereEqualTo("idMerchant", idMerchant);
            List<MerchantProduct> merchantProductList = getMerchantProductsList(query);
            return MerchantProductDto.builder().merchantProducts(merchantProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public MerchantProductDto findActiveByCatalogProduct(String idCatalogProduct) {
        try {
            // whereNotEqualTo
            Query query = firestore.collection(COLLECTION_NAME)
                    .whereEqualTo("status", true)
                    .whereEqualTo("idCatalogProduct", idCatalogProduct);
                    
            List<MerchantProduct> merchantProductList = getMerchantProductsList(query);
            return MerchantProductDto.builder().merchantProducts(merchantProductList).build();
        } catch (InterruptedException | ExecutionException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
