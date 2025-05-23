package sakhno.psup.storage_service.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "stored_products")
public class StoredProductEntity {
    @Id
    private Long id;

    private String name;
}
