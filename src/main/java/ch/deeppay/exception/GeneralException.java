package ch.deeppay.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralException extends AbstractThrowableProblem {

  private final String title;
  private final String detail;
  private final HttpStatus httpStatus;

  public GeneralException(@NonNull final String title, @NonNull final String detail,
                          @NonNull final HttpStatus httpStatus) {
    this.title = title;
    this.detail = detail;
    this.httpStatus = httpStatus;
  }

  @Override
  public String getDetail() {
    return detail;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public StatusType getStatus() {
    return mapStatus(httpStatus);
  }

  @Nullable
  public static StatusType mapStatus(@NonNull final HttpStatus httpStatus) {
    for (StatusType v : Status.values()) {
      if (v.getStatusCode() == httpStatus.value()) {
        return v;
      }
    }
    return null;

  }
}
