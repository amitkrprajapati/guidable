package in.guidable.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
public class SharableResourceView {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    String userToken;
    String resourceId;
}
