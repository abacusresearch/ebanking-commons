package ch.deeppay.service.business;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.exception.DeepPayProblemType;
import ch.deeppay.util.FileFormat;

public abstract class AbstractFactory<T extends NamedService> {

  private final Map<FileFormat, T> map;

  protected AbstractFactory(List<T> list) {
    this.map = list
        .stream()
        .collect(Collectors.toMap(NamedService::getFormat, Function.identity()));
  }

  public T getInstance(@Nonnull final FileFormat fileFormat) {
    final T result = map.get(fileFormat);
    if (Objects.isNull(result)) {
      throw DeepPayProblemException.createProblemException(DeepPayProblemType.INVALID_PARAMETER, String.format("Invalid format: [%s]", fileFormat.name()));
    } else {
      return result;
    }
  }
}
