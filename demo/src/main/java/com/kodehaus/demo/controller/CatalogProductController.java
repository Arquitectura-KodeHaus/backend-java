package com.kodehaus.demo.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kodehaus.demo.dto.CatalogProductDto;
import com.kodehaus.demo.model.CatalogProduct;
import com.kodehaus.demo.service.CatalogProductService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/CatalogProducts")
@AllArgsConstructor
public class CatalogProductController {
    private final CatalogProductService catalogProductService;
    
    @PostMapping()
    public ResponseEntity<String> createCatalogProduct(@RequestBody CatalogProduct catalogProduct){
        return ResponseEntity.ok(catalogProductService.createCatalogProduct(catalogProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CatalogProduct> getCatalogProduct(@PathVariable("id") String id){
        return ResponseEntity.ok(catalogProductService.getCatalogProduct(id));
    }

    @GetMapping()
    public ResponseEntity<CatalogProductDto> getAll() {
        return ResponseEntity.ok(catalogProductService.getAllCatalogProducts());
    }
    

    @PutMapping()
    public ResponseEntity<String> updateCatalogProduct(@RequestBody Map<String, Object> catalogProductUpdate){
        return ResponseEntity.ok(catalogProductService.updateCatalogProduct(catalogProductUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCatalogProduct(@PathVariable("id") String id){
        return ResponseEntity.ok(catalogProductService.deleteCatalogService(id));
    }

    @GetMapping("/find-by-type")
    public ResponseEntity<CatalogProductDto> findByType(@RequestParam String type){
        return ResponseEntity.ok(catalogProductService.findByType(type));
    }
}
