package ch.deeppay.models.ebanking.payment;

import java.util.List;

import ch.deeppay.models.ebanking.ClientResponse;
import ch.deeppay.models.ebanking.login.Challenge;
import ch.deeppay.spring.openapi.ebanking.OpenApiPaymentResponseChallenge;
import ch.deeppay.spring.openapi.ebanking.OpenApiPaymentResponseSimple;
import ch.deeppay.util.LoginState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class PaymentResponse extends ClientResponse implements OpenApiPaymentResponseSimple, OpenApiPaymentResponseChallenge {

  private String file;
  private LoginState state;
  private Challenge challenge;
  private String paymentReference;

  @Builder
  public PaymentResponse(String message, String transportData, List<String> errors, String file, LoginState state, Challenge challenge, String paymentReference) {
    super(message, transportData, errors);
    this.file = file;
    this.state = state;
    this.challenge = challenge;
    this.paymentReference = paymentReference;
  }

}
