package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder(toBuilder = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id",column = @Column(name = "journey_id",length = 16))
public class Journey extends BaseEntity{
    private String name;
    private String description;
    private String originalAuthor;
    private String updatedBy;
    @Embedded
    private PublicMetadata publicMetadata;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_Id")
    private Customer customer;

    @OneToMany
    @JoinColumn(name = "journey_id")
    private List<Roadmap> roadmaps = new ArrayList<>();
}
