package sakhno.psup.storage_service.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import sakhno.psup.storage_service.models.StoredProductEntity;

public interface StoredProductRepository extends ReactiveCrudRepository<StoredProductEntity, Long> {
}
