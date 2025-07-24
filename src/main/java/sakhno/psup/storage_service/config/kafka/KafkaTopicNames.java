package sakhno.psup.storage_service.config.kafka;

import lombok.Getter;

@Getter
public enum KafkaTopicNames {

    MANUFACTURE_TEST_TOPIC("manufacture-test-topic"),
    STORE_TEST_TOPIC("store-test-topic");

    private final String topicName;

    KafkaTopicNames(String topicName) {
        this.topicName = topicName;
    }
}
