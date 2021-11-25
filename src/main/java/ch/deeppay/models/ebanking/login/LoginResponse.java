package ch.deeppay.models.ebanking.login;

import ch.deeppay.models.ebanking.ClientResponse;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep1;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep2;
import ch.deeppay.util.LoginState;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class LoginResponse extends ClientResponse implements OpenApiLoginResponseStep1, OpenApiLoginResponseStep2 {

  private LoginState state;
  private Challenge challenge;
  private String sessionId;
  private String accessToken;
  private String refreshToken;

}
