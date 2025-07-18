package sakhno.psup.storage_service;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Hooks;

//TODO: Перевести kafka в реактивный режим
@SpringBootApplication
public class StorageServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(StorageServiceApplication.class, args);
    }

    @PostConstruct
    public  void  init () {
        Hooks.enableAutomaticContextPropagation();
    }

}
