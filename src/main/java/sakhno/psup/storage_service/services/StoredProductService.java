package sakhno.psup.storage_service.services;

import reactor.core.publisher.Flux;
import sakhno.psup.storage_service.models.StoredProductEntity;

public interface StoredProductService {

    Flux<StoredProductEntity> getAllStoredProducts();
}
