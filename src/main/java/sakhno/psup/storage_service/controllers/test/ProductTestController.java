package sakhno.psup.storage_service.controllers.test;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sakhno.psup.storage_service.services.web_client.web_client_test.ProductServiceTestClient;

@RestController
@RequestMapping("api/v1/storage-service/test/product")
@RequiredArgsConstructor
public class ProductTestController {
    private final ProductServiceTestClient productServiceTestClient;

    @GetMapping("/success")
    public ResponseEntity<Mono<String>> storageSuccess() {
        System.out.println("success: " + Thread.currentThread().getName());
        return ResponseEntity.ok(productServiceTestClient.successTestRequest());
    }

    @GetMapping("/timeout")
    public ResponseEntity<Mono<String>> storageTimeout() {
        System.out.println("timeout: " + Thread.currentThread().getName());
        return ResponseEntity.ok(productServiceTestClient.timeoutTestRequest());
    }

    @GetMapping("/error")
    public ResponseEntity<Mono<String>> storageError() {
        return ResponseEntity.ok(productServiceTestClient.errorTestRequest());
    }

    @GetMapping("/bad-request")
    public ResponseEntity<Mono<String>> storageBadRequest() {
        return ResponseEntity.ok(productServiceTestClient.badRequestTestRequest());
    }

    @GetMapping("/unprocessable-entity")
    public ResponseEntity<Mono<String>> storageUnprocessableEntity() {
        return ResponseEntity.ok(productServiceTestClient.unprocessableEntityTestRequest());
    }

    @GetMapping("/forbidden")
    public ResponseEntity<Mono<String>> storageForbidden() {
        return ResponseEntity.ok(productServiceTestClient.forbiddenTestRequest());
    }

    @GetMapping("/not-found")
    public ResponseEntity<Mono<String>> storageNotFound() {
        return ResponseEntity.ok(productServiceTestClient.notFoundTestRequest());
    }

    @GetMapping("/unauthorized")
    public ResponseEntity<Mono<String>> storageUnauthorized() {
        return ResponseEntity.ok(productServiceTestClient.unauthorizedTestRequest());
    }
}
