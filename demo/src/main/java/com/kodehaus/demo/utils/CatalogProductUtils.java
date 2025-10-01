package com.kodehaus.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kodehaus.demo.model.CatalogProduct;

public class CatalogProductUtils {
    public static List<CatalogProduct> getCatalogProductsList(Query query)throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshotApiFuture.get().getDocuments();
        List<CatalogProduct> catalogProductList = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : documents) {
            CatalogProduct catalogProducts = queryDocumentSnapshot.toObject(CatalogProduct.class);
            catalogProductList.add(catalogProducts);
        }
        return catalogProductList;
    }
}
