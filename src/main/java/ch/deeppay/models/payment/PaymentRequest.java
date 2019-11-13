package ch.deeppay.models.payment;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentRequest extends ClientRequest {

  @FileFormatConstraint private String format;
  @Nullable private final String challenge;
  @Nullable private final Boolean details;
  @Nullable private final String deviceNote;

  public FileFormat getFormat() {
    return FileFormat.valueOf(format);
  }
}
