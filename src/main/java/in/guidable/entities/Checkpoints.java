package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id",column = @Column(name = "checkpoint_id",length = 16))
public class Checkpoints extends BaseEntity{
    private  String name;
    private  String quickLink;
    private  String shortDescription;
    private  String detailedDescription;
    private  boolean isComplete;
    @ManyToOne
    @JoinColumn(name = "roadmap_id", referencedColumnName = "roadmap_id")
    private Roadmap roadmap;

    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "customer_Id")
    private Customer customer;
}
