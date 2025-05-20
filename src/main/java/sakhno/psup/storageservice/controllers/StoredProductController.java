package sakhno.psup.storageservice.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import sakhno.psup.storageservice.models.StoredProductEntity;
import sakhno.psup.storageservice.services.StoredProductService;

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
