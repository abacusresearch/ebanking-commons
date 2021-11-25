package ch.deeppay.models.ebanking.payment;

import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.spring.openapi.ebanking.OpenAPiPaymentChallengeRequest;
import ch.deeppay.spring.openapi.ebanking.OpenAPiPaymentRequest;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class PaymentRequest implements OpenAPiPaymentRequest, OpenAPiPaymentChallengeRequest {

  private String transportData;
  @FileFormatConstraint private String format;
  @Nullable private String challenge;
  @Nullable private Boolean details;
  @Nullable private String deviceNote;

  public FileFormat getFormat() {
    return FileFormat.valueOf(format);
  }

  @Nullable
  public FileFormat getFormatNullable() {
    return FileFormat.fromStr(format);
  }
}
