package sakhno.psup.storageservice.services;

import reactor.core.publisher.Flux;
import sakhno.psup.storageservice.models.StoredProductEntity;

public interface StoredProductService {

    Flux<StoredProductEntity> getAllStoredProducts();
}
