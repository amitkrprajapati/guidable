package in.guidable.entities;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "CUSTOMER_TBL")
@AttributeOverride(name = "id", column = @Column(name = "customer_id", length = 16))
public class Customer extends BaseEntity {

  private String customerUserName;
  private String customerEmail;
  private String customerPassword;
  private boolean isEnabled;

  @Enumerated(EnumType.STRING)
  private RoleType userRole;

  // TODO roadMapCollection

}
