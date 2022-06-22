package in.guidable.entities;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@Table(name = "CUSTOMER_TBL")
public class Customer {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "in.guidable.repositories.generator.CustomUUIDGenerator")
    @Column(length = 16)
    private UUID customerId;

    private String customerUserName;
    private String customerEmail;
    private String customerPassword;

    @Enumerated(EnumType.STRING)
    private RoleType userRole;

    //TODO roadMapCollection

}
