package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;

@Entity
@Builder
@Getter
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
    @Embedded
    private PublicMetadata publicMetadata;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "roadmap_id")
    private List<Checkpoints> checkpoints;
}
