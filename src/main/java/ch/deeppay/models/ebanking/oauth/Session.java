package ch.deeppay.models.ebanking.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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