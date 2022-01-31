package ch.deeppay.models.ebanking.login;

import ch.deeppay.spring.constraints.LanguageConstraint;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestCertificate;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestChallenge;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestOAuth;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Bean for each request on the login route.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class LoginRequest implements OpenApiLoginRequestCertificate, OpenApiLoginRequestPassword, OpenApiLoginRequestChallenge, OpenApiLoginRequestOAuth {

  private String contractId;
  private String password;
  private String challenge;
  private String passwordNew;
  private String accessToken;
  private String refreshToken;
  private String bankId;
  @LanguageConstraint
  private String language;
  private String transportData;
  private String url;
  private String participantId;
  private String accessMethod;

  public void setLanguage(String language) {
    if (language != null) {
      this.language = language.toLowerCase();
    }
  }


}
