package ch.deeppay.models.ebanking.login;

import ch.deeppay.models.ebanking.ClientResponse;
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
public class LoginResponse extends ClientResponse {

  private LoginState state;
  private Challenge challenge;
  private String sessionId;

}