package in.guidable.entities;

import in.guidable.model.SharableResourceResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.UniqueConstraint;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SharableLinkKeyResourceMap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String resourceId;
    private SharableResourceResponse.ObjectTypeEnum objectType;
    private String userId;

    @Column(unique = true)
    private String linkKey;
    private Boolean isEnabled;
}
