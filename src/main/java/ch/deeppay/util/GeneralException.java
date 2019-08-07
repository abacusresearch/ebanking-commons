package ch.deeppay.util;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;
import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;
import org.zalando.problem.StatusType;

import javax.annotation.Nonnull;

@EqualsAndHashCode(callSuper = true)
@Data
public class GeneralException extends AbstractThrowableProblem {

  private final String title;
  private final String detail;
  private final HttpStatus httpStatus;

  public GeneralException(@Nonnull final String title, @Nonnull final String detail,
                          @Nonnull final HttpStatus httpStatus) {
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

  public static StatusType mapStatus(@Nonnull final HttpStatus httpStatus) {
    for (StatusType v : Status.values()) {
      if (v.getStatusCode() == httpStatus.value()) {
        return v;
      }
    }
    return null;

  }
}
