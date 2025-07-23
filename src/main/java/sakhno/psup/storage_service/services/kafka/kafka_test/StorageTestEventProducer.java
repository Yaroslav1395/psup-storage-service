package sakhno.psup.storage_service.services.kafka.kafka_test;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import sakhno.psup.storage_service.config.kafka.KafkaTopicNames;
import sakhno.psup.storage_service.dto.ProductKafkaTestDto;
import sakhno.psup.storage_service.events.producer.ProductTestEvent;

import java.util.Random;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class StorageTestEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTestMessageToTopicManufactureTestEvent(ProductKafkaTestDto productKafkaTestDto) {
        log.info("Отработка отправки сообщения в очередь: {}", productKafkaTestDto.getMessage());

        Long id = new Random().nextLong();

        log.info("Сгенерирован ID: {}", id);

        ProductTestEvent productTestEvent = ProductTestEvent.builder()
                .id(id)
                .message(productKafkaTestDto.getMessage() + " + storage-service")
                .build();

        ProducerRecord<String, Object> record = new ProducerRecord<>(
                KafkaTopicNames.STORE_TEST_TOPIC.getTopicName(), id.toString(), productTestEvent);
        record.headers().add("messageId", UUID.randomUUID().toString().getBytes());

        kafkaTemplate.send(record)
                .thenAccept(result -> log.info("Сообщение отправлено с офсетом: {}", result.getRecordMetadata().offset()))
                .exceptionally(ex -> {
                    log.error("Сообщение не отправлено", ex);
                    return null;
                });

    }
}
