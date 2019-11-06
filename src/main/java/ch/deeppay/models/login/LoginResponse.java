package ch.deeppay.models.login;

import ch.deeppay.models.ClientResponse;
import ch.deeppay.util.LoginState;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class LoginResponse extends ClientResponse {

  private LoginState state;
  private Challenge challenge = null;
  private String sessionId = null;

}
