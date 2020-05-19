package ch.deeppay.models.payment;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentRequest extends ClientRequest {

  @FileFormatConstraint private String format;
  @Nullable private String challenge;
  @Nullable private Boolean details;
  @Nullable private String deviceNote;

  public FileFormat getFormat() {
    return FileFormat.valueOf(format);
  }
}
