package com.kodehaus.demo.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.kodehaus.demo.model.MerchantProduct;

public class MerchantProductUtils {
    public static List<MerchantProduct> getMerchantProductsList(Query query)throws InterruptedException, ExecutionException {
        ApiFuture<QuerySnapshot> querySnapshotApiFuture = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshotApiFuture.get().getDocuments();
        List<MerchantProduct> merchantProductList = new ArrayList<>();
        for (QueryDocumentSnapshot queryDocumentSnapshot : documents) {
            MerchantProduct merchantProducts = queryDocumentSnapshot.toObject(MerchantProduct.class);
            merchantProductList.add(merchantProducts);
        }
        return merchantProductList;
    }
}
