package ch.deeppay.models.login;

import ch.deeppay.models.ClientResponse;
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
  private Challenge challenge = null;
  private String sessionId = null;

}
