package ch.deeppay.models.payment;

import ch.deeppay.models.ClientResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentResponse extends ClientResponse {

  private String file;
}
