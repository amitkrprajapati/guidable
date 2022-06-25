package in.guidable.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;

@Embeddable
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PublicMetadata {
    private Long viewCount = 0L;
    private Long likeCount = 0L;
    private String linkKey = "";
    private Boolean isSharable = false;

}
