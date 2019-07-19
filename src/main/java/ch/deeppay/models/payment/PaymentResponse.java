package ch.deeppay.models.payment;

import ch.deeppay.models.ClientResponse;
import ch.deeppay.models.login.Challenge;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentResponse extends ClientResponse {

  private String file;
  private String state;
  private Challenge challenge;
}
