package sakhno.psup.storageservice.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import sakhno.psup.storageservice.models.StoredProductEntity;
import sakhno.psup.storageservice.repositories.StoredProductRepository;

@Service
@RequiredArgsConstructor
public class StoredProductServiceImpl implements StoredProductService {
    private final StoredProductRepository storedProductRepository;

    @Override
    public Flux<StoredProductEntity> getAllStoredProducts() {
        return storedProductRepository.findAll();
    }
}
