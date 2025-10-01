package com.kodehaus.demo.service;

import com.google.cloud.firestore.Firestore;
import com.kodehaus.demo.dto.CatalogProductWithMerchantsDTO;
import com.kodehaus.demo.dto.MerchantProductListingDTO;
import com.kodehaus.demo.model.CatalogProduct;
import com.kodehaus.demo.model.MerchantProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor // Usaremos esto en lugar de @AllArgsConstructor para inyección de dependencias
public class ExploreService {

    private final MerchantProductService merchantProductService;
    private final CatalogProductService catalogProductService;
    private final Firestore firestore; // Para acceder directamente a otras colecciones si es necesario
    
    // --- NUEVO MÉTODO CLAVE ---
    public List<CatalogProductWithMerchantsDTO> getGroupedProductsForCustomer() {
        
        // 1. Obtener todos los productos del catálogo
        List<CatalogProduct> catalogProducts = catalogProductService.getAllCatalogProducts().getCatalogProducts();

        // 2. Mapear cada producto del catálogo a la estructura agrupada
        return catalogProducts.stream()
                .map(this::buildGroupedProduct)
                .filter(dto -> dto.getMerchantCount() > 0) // Solo incluir productos con al menos 1 vendedor activo
                .collect(Collectors.toList());
    }

    private CatalogProductWithMerchantsDTO buildGroupedProduct(CatalogProduct catalogProduct) {
        
        // 1. Obtener los MerchantProducts activos para este CatalogProduct
        // ASUMIMOS que MerchantProduct tiene un campo 'idCatalogProduct' o similar para hacer la consulta.
        
        // **ESTA ES LA CONSULTA CRÍTICA** (Necesitas implementar este nuevo método en MerchantProductService)
        List<MerchantProduct> activeOffers = merchantProductService
            .findActiveByCatalogProduct(catalogProduct.getId())
            .getMerchantProducts();
            
        // 2. Mapear las ofertas de MerchantProduct a MerchantProductListingDTO
        List<MerchantProductListingDTO> merchantListings = activeOffers.stream()
                .map(offer -> mapToMerchantListingDTO(offer, catalogProduct.getBulletinPrice()))
                .collect(Collectors.toList());
        
        // 3. Construir el DTO agrupado
        return CatalogProductWithMerchantsDTO.builder()
                .id(catalogProduct.getId())
                .name(catalogProduct.getName())
                .type(catalogProduct.getType())
                .merchantCount(merchantListings.size())
                .merchants(merchantListings)
                .build();
    }
    
    // Método auxiliar para mapear el MerchantProduct al DTO de listado
    private MerchantProductListingDTO mapToMerchantListingDTO(MerchantProduct offer, double bulletinPrice) {
        // **NOTA:** Aquí necesitarías hacer una consulta a las colecciones 'Vendedor' y 'Puesto' 
        // usando offer.getIdMerchant() para obtener el nombre y el puesto. 
        // Por simplicidad, asumiremos que tienes los servicios o métodos para hacerlo.
        
        // Ejemplo simplificado (Debes obtener los datos del vendedor/puesto de Firestore)
        String merchantName = "Vendedor ID: " + offer.getIdMerchant(); 
        String puestoName = "Puesto X"; 

        return MerchantProductListingDTO.builder()
                .merchantProductId(offer.getId())
                .merchantName(merchantName)
                .puestoName(puestoName)
                .price(offer.getMerchantPrice()) // Precio que vende el comerciante
                .bulletinPrice(bulletinPrice) // Precio del boletín que viene de CatalogProduct
                .stock(10)
                .build();
    }
}