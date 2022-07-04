package in.guidable.repositories.generator;

import java.io.Serializable;
import lombok.NoArgsConstructor;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.UUIDGenerator;

@NoArgsConstructor
public class CustomUUIDGenerator extends UUIDGenerator {

  @Override
  public Serializable generate(SharedSessionContractImplementor session, Object object)
      throws HibernateException {
    Serializable id =
        session.getEntityPersister(null, object).getClassMetadata().getIdentifier(object, session);
    return id != null ? id : super.generate(session, object);
  }
}
