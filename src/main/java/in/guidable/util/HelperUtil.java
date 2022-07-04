package in.guidable.util;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class HelperUtil {

  private static <T> List<T> removeNull(List<T> arr) {

    return arr.stream().filter(Objects::nonNull).collect(Collectors.toList());
  }
}
