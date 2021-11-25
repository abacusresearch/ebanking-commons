package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.constraints.LanguageConstraint;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Interface is only used for open api doc's
 */
@Schema(name="OAuthLoginRequest", description = "OAuth login request for step1")
public interface OpenApiLoginRequestOAuth {

  @Schema(description = "The bankId is the bank identification received by the discovery route. It is used to forward the request to the right bank. ",required = true,example = "8158")
  String getBankId();

  @LanguageConstraint
  @Schema(description = "Language that might be used for result message",required = true, example = "de")
  String getLanguage();

  @Schema(description = "Valid access token ",required = true)
  String getAccessToken();
}