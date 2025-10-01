package com.kodehaus.demo.controller;

import com.kodehaus.demo.dto.CatalogProductWithMerchantsDTO;
import com.kodehaus.demo.service.ExploreService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/explore")
@RequiredArgsConstructor
public class ExploreController {

    private final ExploreService exploreService;

    @GetMapping("/products")
    public ResponseEntity<List<CatalogProductWithMerchantsDTO>> getExploreProducts() {
        List<CatalogProductWithMerchantsDTO> products = exploreService.getGroupedProductsForCustomer();
        
        // Aseguramos que se devuelve un 200 OK con la lista de productos
        return ResponseEntity.ok(products);
    }
}