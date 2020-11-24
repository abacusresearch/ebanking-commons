package ch.deeppay.models.ebanking.payment;

import ch.deeppay.models.ebanking.ClientRequest;
import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class PaymentRequest extends ClientRequest {

  @FileFormatConstraint private String format;
  @Nullable private String challenge;
  @Nullable private Boolean details;
  @Nullable private String deviceNote;

  public FileFormat getFormat() {
    return FileFormat.valueOf(format);
  }
}
