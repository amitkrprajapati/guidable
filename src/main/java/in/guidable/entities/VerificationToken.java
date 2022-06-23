package in.guidable.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Table(name = "VERIFICATION_TOKEN_TBL")
@AttributeOverride(name = "id",column = @Column(name = "verificationTokeId",length = 16))
public class VerificationToken extends BaseEntity{


    private static  final int EXPIRATION_TIME = 10;

    private String token;

    private boolean isVerified;
    private Date expirationTime;
    private Integer retryCount;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name = "FK_USER_VERIFY_TOKEN"))
    private Customer customer;

    public VerificationToken(Customer customer, String token) {
        super();
        this.token = token;
        this.customer = customer;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
        this.creationDate = new Date();
    }

    public VerificationToken(String token) {
        super();
        this.token = token;
        this.expirationTime = calculateExpirationDate(EXPIRATION_TIME);
    }

    private Date calculateExpirationDate(int expirationTime) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }

}
