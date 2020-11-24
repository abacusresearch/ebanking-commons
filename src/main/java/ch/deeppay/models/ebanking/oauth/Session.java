package ch.deeppay.models.ebanking.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Session {

  private String bankId;

  private String clientId;
  private String clientRedirectUrl;
  private String state;

  private String serverAuthorizationUrl;
  private String serverRedirectUrl;
  private String refreshTokenUrl;
  private String scope;

}