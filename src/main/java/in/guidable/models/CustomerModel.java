package in.guidable.models;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerModel {
  String userName;
  UUID userId;
}
