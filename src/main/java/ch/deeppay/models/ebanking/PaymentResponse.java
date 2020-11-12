package ch.deeppay.models.ebanking.payment;

import ch.deeppay.models.ebanking.ClientResponse;
import ch.deeppay.models.ebanking.login.Challenge;
import ch.deeppay.util.LoginState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class PaymentResponse extends ClientResponse {

  private String file;
  private LoginState state;
  private Challenge challenge;
}
