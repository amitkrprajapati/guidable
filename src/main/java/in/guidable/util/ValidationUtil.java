package in.guidable.util;

import in.guidable.exceptions.RenderableException;
import in.guidable.exceptions.RenderableExceptionGenerator;
import java.util.UUID;

public class ValidationUtil {
  public static void validateId(String name, String id) throws RenderableException {
    try {
      final UUID uuid = UUID.fromString(id);
    } catch (Exception e) {
      throw RenderableExceptionGenerator.generateInvalidParameterException(name);
    }
  }
}
