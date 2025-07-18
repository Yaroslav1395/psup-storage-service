package sakhno.psup.storage_service.services.kafka.kafka_test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import sakhno.psup.storage_service.events.consumer.StorageTestEvent;

@Profile({"local", "test", "prod"})
@Component
@KafkaListener(topics = "manufacture-test-topic")
@RequiredArgsConstructor
@Slf4j
public class ManufactureTestEventHandler {

    @KafkaHandler
    public void handle(@Payload StorageTestEvent storageTestEvent, @Header("messageId") String id,
                       @Header(KafkaHeaders.RECEIVED_KEY) String messageKey) {
        log.info("Из очереди manufacture-test-topic получено сообщение: {}. Ключ сообщения: {}. ID сообщения: {}",
                storageTestEvent.getMessage(), messageKey, id);
        //TODO: отправить сообщение
    }

}
