package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id",column = @Column(name = "roadmap_id",length = 16))
public class Roadmap extends SharableEntity  {
    private String name;
    private String description;
    private String originalAuthor;
    private String updatedBy;

    @Column(length = 16)
    private UUID customerId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "journey_id", referencedColumnName = "journey_Id")
    private Journey journey;
}
