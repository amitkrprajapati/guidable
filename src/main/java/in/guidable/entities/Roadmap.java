package in.guidable.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.Value;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

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
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private String id;
    private String name;
    private String description;
    private String parentId;
    private String originalAuthor;
    private String updatedBy;
    private boolean isSharable;

    @OneToMany
    @Cascade(CascadeType.ALL)
    @JoinColumn(name = "roadmap_id")
    private List<Checkpoints> checkpoints;

    @Override
    public boolean getIsSharable() {
        return isSharable;
    }
}
