package sakhno.psup.storage_service.controllers.test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import sakhno.psup.storage_service.services.web_client.web_client_test.ProductServiceTestClient;

@RestController
@RequestMapping("api/v1/storage-service/test/product/tracing")
@RequiredArgsConstructor
@Slf4j
public class TracingTestController {
    private final ProductServiceTestClient productServiceTestClient;

    @GetMapping("/success")
    public ResponseEntity<Mono<String>> storageSuccess() {
        log.info("success: {}", Thread.currentThread().getName());
        return ResponseEntity.ok(productServiceTestClient.successTestRequest());
    }
}
