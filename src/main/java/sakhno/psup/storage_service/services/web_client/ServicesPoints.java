package sakhno.psup.storage_service.services.web_client;

import lombok.Getter;

@Getter
public enum ServicesPoints {
    PRODUCT_TEST_SUCCESS("Отвечает успехом", "api/v1/product-service/test/ok"),
    PRODUCT_TEST_ERROR("Отвечает ошибкой", "api/v1/product-service/test"),
    PRODUCT_TEST_TIMEOUT("Выдает таймаут", "api/v1/product-service/test/timeout"),
    PRODUCT_TEST_BAD_REQUEST("Выдает некорректный запрос", "api/v1/product-service/test/bad-request"),
    PRODUCT_TEST_UNPROCESSABLE_ENTITY("Выдает неподдерживаемый тип объекта", "api/v1/product-service/test/unprocessable-entity"),
    PRODUCT_TEST_FORBIDDEN("Выдает ошибку сервера", "api/v1/product-service/test/forbidden"),
    PRODUCT_TEST_UNAUTHORIZED("Выдает ошибку сервера", "api/v1/product-service/test/unauthorized"),
    PRODUCT_TEST_NOT_FOUND("Выдает ошибку ненайденного ресурса", "api/v1/product-service/test//not-found"),;

    private final String description;
    private final String point;

    ServicesPoints(String description, String point) {
        this.description = description;
        this.point = point;
    }

}
