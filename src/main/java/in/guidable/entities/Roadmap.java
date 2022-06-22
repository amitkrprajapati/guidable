package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Roadmap implements SharableResource{
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "in.guidable.repositories.generator.CustomUUIDGenerator"
    )
    private String id;
    private String name;
    private String description;
    private String parentId;
    private String originalAuthor;
    private String updatedBy;
    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_customer_id", referencedColumnName = "customerId")
    private Customer customer;
    @Embedded
    private PublicMetadata publicMetadata;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "roadmap_id")
    private List<Checkpoints> checkpoints;
}
