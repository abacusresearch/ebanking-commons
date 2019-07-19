package ch.deeppay.models.payment;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.util.FileFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

import javax.validation.constraints.NotNull;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentRequest extends ClientRequest {

  @NotNull private FileFormat format;
  private String challenge;

  public void setFormat(final @NonNull String format) {
    this.format = FileFormat.validateUpload(format);
  }
}
