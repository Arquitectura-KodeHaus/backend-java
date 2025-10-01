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
import org.springframework.web.bind.annotation.RestController;

import com.kodehaus.demo.dto.MerchantProductDto;
import com.kodehaus.demo.model.MerchantProduct;
import com.kodehaus.demo.service.MerchantProductService;

import lombok.AllArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/MerchantProducts")
@AllArgsConstructor
public class MerchantProductController {
    private final MerchantProductService merchantProductService;
    
    @PostMapping()
    public ResponseEntity<String> createMerchantProduct(@RequestBody MerchantProduct merchantProduct){
        return ResponseEntity.ok(merchantProductService.createMerchantProduct(merchantProduct));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MerchantProduct> getMerchantProduct(@PathVariable("id") String id){
        return ResponseEntity.ok(merchantProductService.getMerchantProduct(id));
    }

    @GetMapping()
    public ResponseEntity<MerchantProductDto> getAll() {
        return ResponseEntity.ok(merchantProductService.getAllMerchantProducts());
    }

    @GetMapping("/merchant-active/{id}")
    public ResponseEntity<MerchantProductDto> getMerchantActiveProducts(@PathVariable("id") String id){
        return ResponseEntity.ok(merchantProductService.findActiveByMerchant(id));
    }

    @GetMapping("/merchant/{id}")
    public ResponseEntity<MerchantProductDto> getMerchantProducts(@PathVariable("id") String id){
        return ResponseEntity.ok(merchantProductService.findByMerchant(id));
    }
    

    @PutMapping()
    public ResponseEntity<String> updateMerchantProduct(@RequestBody Map<String, Object> merchantProductUpdate){
        return ResponseEntity.ok(merchantProductService.updateMerchantProduct(merchantProductUpdate));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMerchantProduct(@PathVariable("id") String id){
        return ResponseEntity.ok(merchantProductService.deleteMerchantService(id));
    }

    @GetMapping("/find-active")
    public ResponseEntity<MerchantProductDto> findActive(){
        return ResponseEntity.ok(merchantProductService.findActive());
    }
}
