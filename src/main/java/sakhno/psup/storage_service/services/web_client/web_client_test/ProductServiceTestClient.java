package sakhno.psup.storage_service.services.web_client.web_client_test;


import reactor.core.publisher.Mono;

//TODO: Удалить после отладки
public interface ProductServiceTestClient {
    Mono<String> successTestRequest();

    Mono<String> errorTestRequest();

    Mono<String> timeoutTestRequest();

    Mono<String> badRequestTestRequest();

    Mono<String> unprocessableEntityTestRequest();

    Mono<String> forbiddenTestRequest();

    Mono<String> notFoundTestRequest();

    Mono<String> unauthorizedTestRequest();
}
