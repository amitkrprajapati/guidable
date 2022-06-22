package in.guidable.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "CUSTOMER_TBL")
@AttributeOverride(name = "id",column = @Column(name = "customerId",length = 16))
public class Customer extends BaseEntity{


    private String customerUserName;
    private String customerEmail;
    private String customerPassword;
    private boolean isEnabled;

    @Enumerated(EnumType.STRING)
    private RoleType userRole;

    //TODO roadMapCollection

}
