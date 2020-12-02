package ch.deeppay.models.ebanking.login;

import ch.deeppay.spring.constraints.LanguageConstraint;
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
public class LoginRequest {

  private String contractId;
  private String password;
  private String challenge;
  private String passwordNew;
  private String accessToken;
  private String bankId;
  @LanguageConstraint
  private String language;
  private String transportData;
  private String url;

  public void setLanguage(String language) {
    if (language != null) {
      this.language = language.toLowerCase();
    }
  }
}
