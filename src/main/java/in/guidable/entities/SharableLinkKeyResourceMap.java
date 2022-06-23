package in.guidable.entities;

import in.guidable.model.SharableResourceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;


@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@AttributeOverride(name = "id",column = @Column(name = "link_id",length = 16))
public class SharableLinkKeyResourceMap extends BaseEntity {
    private String resourceId;
    private SharableResourceResponse.ObjectTypeEnum objectType;
    private String userId;

    @Column(unique = true)
    private String linkKey;
    private Boolean isEnabled;
}
