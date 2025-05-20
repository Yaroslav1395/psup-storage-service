package sakhno.psup.storageservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import sakhno.psup.storageservice.models.StoredProductEntity;

public interface StoredProductRepository extends ReactiveCrudRepository<StoredProductEntity, Long> {
}
