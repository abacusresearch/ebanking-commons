package ch.deeppay.models.login;

import ch.deeppay.spring.constraints.LanguageConstraint;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

/**
 * Bean for each request on the login route.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class LoginRequest {

  @Builder.Default
  private String contractId = StringUtils.EMPTY;
  @Builder.Default
  private String password = StringUtils.EMPTY;
  @Builder.Default
  private String challenge = StringUtils.EMPTY;
  @Builder.Default
  private String passwordNew = StringUtils.EMPTY;
  @Builder.Default
  private String accessToken = StringUtils.EMPTY;
  private String bankId;
  @Builder.Default
  @LanguageConstraint
  private String language = StringUtils.EMPTY;
  @Builder.Default
  private String transportData = StringUtils.EMPTY;

  public void setLanguage(String language) {
    if (language != null) {
      this.language = language.toLowerCase();
    }
  }
}
