package sakhno.psup.storage_service.services.web_client.web_client_test;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.github.resilience4j.reactor.retry.RetryOperator;
import io.github.resilience4j.reactor.timelimiter.TimeLimiterOperator;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryRegistry;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import sakhno.psup.storage_service.services.web_client.ServicesPoints;
import sakhno.psup.storage_service.utils.TraceContextUtils;

import java.util.concurrent.TimeoutException;

//TODO: Удалить после отладки

/**
 * При использовании конечного автомата нужно учитывать:
 * 1. Конечный автомат не работает в реактивном режиме через аннотации.
 * 2. Для WebClient в асинхронном режиме конечный автомат настраивается через методы.
 * 3. Нужно учитывать последовательность установки timeLimiter, circuitBreaker, retry. Иначе будет работать некорректно.
 * 4. Необходимо использовать retry только при идемпотентных запросах. Иначе при таймауте будет дублирование данных.
 * 5. TimeLimiter устанавливать в соответствии с документацией запрашиваемой api.
 * 6. Необходимо тонко настраивать ошибки при которых конечный автомат переходит в открытое состояние.
 * 7. Fallback метод должен быть единым, иначе цепочка timeLimiter, circuitBreaker, retry не будет корректно отрабатывать.
 */
@Service
public class ProductServiceTestClientImpl implements ProductServiceTestClient {
    private final WebClient webClient;
    private final Retry retry;
    private final TimeLimiter timeLimiter;
    private final CircuitBreaker circuitBreaker;
    private final TraceContextUtils traceContextUtils;

    public ProductServiceTestClientImpl(
            @Qualifier("productServiceWebClient") WebClient productWebClient,
            RetryRegistry retryRegistry,
            TimeLimiterRegistry timeLimiterRegistry,
            CircuitBreakerRegistry circuitBreakerRegistry,
            TraceContextUtils traceContextUtils) {
        this.webClient = productWebClient;
        this.retry = retryRegistry.retry("PRODUCT-SERVICE");
        this.timeLimiter = timeLimiterRegistry.timeLimiter("PRODUCT-SERVICE");
        this.circuitBreaker = circuitBreakerRegistry.circuitBreaker("PRODUCT-SERVICE");
        this.traceContextUtils = traceContextUtils;
    }

    @Override
    public Mono<String> successTestRequest() {
        return traceContextUtils.withTraceParent(traceParent -> webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_SUCCESS.getPoint())
                .headers(headers -> traceContextUtils.setTraceToHeaders(traceParent, headers))
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> System.out.println("Success received response in thread: " + Thread.currentThread().getName()))
                .transformDeferred(TimeLimiterOperator.of(timeLimiter))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(Exception.class, this::fallback)
        );
    }

    @Override
    public Mono<String> errorTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_ERROR.getPoint())
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> {
                    System.out.println("Timeout received response in thread: " + Thread.currentThread().getName());
                })
                .transformDeferred(TimeLimiterOperator.of(timeLimiter))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(Exception.class, this::fallback);
    }

    @Override
    public Mono<String> timeoutTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_TIMEOUT.getPoint())
                .retrieve()
                .bodyToMono(String.class)
                .transformDeferred(TimeLimiterOperator.of(timeLimiter))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .transformDeferred(RetryOperator.of(retry))
                .onErrorResume(Exception.class, this::fallback);
    }

    @Override
    public Mono<String> badRequestTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_BAD_REQUEST.getPoint())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> unprocessableEntityTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_UNPROCESSABLE_ENTITY.getPoint())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> forbiddenTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_FORBIDDEN.getPoint())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> notFoundTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_NOT_FOUND.getPoint())
                .retrieve()
                .bodyToMono(String.class);
    }

    @Override
    public Mono<String> unauthorizedTestRequest() {
        return webClient
                .get()
                .uri(ServicesPoints.PRODUCT_TEST_UNAUTHORIZED.getPoint())
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> fallback(Throwable t) {
        if (t instanceof TimeoutException) {
            System.out.println("Circuit breaker fallback triggered: " + t.getClass().getSimpleName());
            return Mono.just("Сработал лимит времени при запросе на сервис каталога. Причина: " + t.getMessage());
        }
        System.out.println("Circuit breaker fallback triggered: " + t.getClass().getSimpleName());
        return Mono.just("Сработал конечный автомат при запросе на сервис каталога. Причина: " + t.getMessage());
    }
}
