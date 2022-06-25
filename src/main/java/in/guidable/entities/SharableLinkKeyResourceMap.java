package in.guidable.entities;

import in.guidable.model.PublicResourceType;
import in.guidable.model.SharableResourceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.UUID;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id",column = @Column(name = "link_id",length = 16))
public class SharableLinkKeyResourceMap extends BaseEntity {
    private UUID resourceId;
    private PublicResourceType objectType;
    private UUID customerId;

    @Column(unique = true)
    private String linkKey;
    private Boolean isEnabled;
}
