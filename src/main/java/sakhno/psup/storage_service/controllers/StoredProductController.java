package sakhno.psup.storage_service.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sakhno.psup.storage_service.models.StoredProductEntity;
import sakhno.psup.storage_service.services.StoredProductService;

@RestController
@RequestMapping("stored/products")
@RequiredArgsConstructor
public class StoredProductController {
    private final StoredProductService storedProductService;

    @GetMapping
    public Flux<StoredProductEntity> getAllProducts(){
        return storedProductService.getAllStoredProducts();
    }
}
