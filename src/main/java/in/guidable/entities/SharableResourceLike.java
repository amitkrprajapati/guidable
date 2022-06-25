package in.guidable.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Getter
@Setter
@AttributeOverride(name = "id",column = @Column(name = "like_id",length = 16))
public class SharableResourceLike extends BaseEntity{

    UUID customerId;
    UUID resourceId;

}
