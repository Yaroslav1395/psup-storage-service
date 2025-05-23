package sakhno.psup.storage_service.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import sakhno.psup.storage_service.models.StoredProductEntity;
import sakhno.psup.storage_service.repositories.StoredProductRepository;

@Service
@RequiredArgsConstructor
public class StoredProductServiceImpl implements StoredProductService {
    private final StoredProductRepository storedProductRepository;

    @Override
    public Flux<StoredProductEntity> getAllStoredProducts() {
        return storedProductRepository.findAll();
    }
}
