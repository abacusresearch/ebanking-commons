package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.constraints.LanguageConstraint;
import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_BANK_ID_DESCRIPTION;

/**
 * Interface is only used for open api doc's
 */
@Schema(name="OAuthLoginRequest", description = "OAuth login request for step1")
public interface OpenApiLoginRequestOAuth {

  @Schema(description = SCHEMA_BANK_ID_DESCRIPTION, required = true, example = "8158")
  String getBankId();

  @LanguageConstraint
  @Schema(description = "Language that might be used for result message",required = true, example = "de")
  String getLanguage();

  @Schema(description = "Valid access token ",required = true)
  String getAccessToken();
}