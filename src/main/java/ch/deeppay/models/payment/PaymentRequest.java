package ch.deeppay.models.payment;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.util.FileFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.lang.Nullable;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentRequest extends ClientRequest {

  @Nullable private FileFormat format;
  @Nullable private final String challenge;
  @Nullable private final Boolean details;
  @Nullable private final String deviceNote;

  public void setFormat(final String format) {
    this.format = FileFormat.validateUpload(format);
  }
}
