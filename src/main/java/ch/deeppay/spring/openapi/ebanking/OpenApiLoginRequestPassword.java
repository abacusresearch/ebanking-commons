package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.constraints.LanguageConstraint;
import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_BANK_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_LANGUAGE_DESCRIPTION;

/**
 * Interface is only used for open api doc's
 */
@Schema(name="PasswordLoginRequest", description = "Password login request for step1")
public interface OpenApiLoginRequestPassword {
  @Schema(description = "ContractId that is given by the financial institute e-banking contract",required = true, example = "700-1234-5678")
  String getContractId();

  @Schema(description = "Passwort of the contract", required = true,example = "1234567890")
  String getPassword();

  @Schema(description = SCHEMA_BANK_ID_DESCRIPTION, required = true, example = "8158")
  String getBankId();

  @LanguageConstraint
  @Schema(description = SCHEMA_LANGUAGE_DESCRIPTION, required = true, example = "de")
  String getLanguage();

  @Schema(description = "Optional path to connect to an institution. If this parameter is omitted, the bankId parameter is used to identify the institution path.", example = "https://test.bank.ch")
  String getUrl();

  @Schema(description = "Optional participant id if supported.", example = "555673AB")
  String getParticipantId();

  @Schema(description = "Optional access method if supported.", example = "MobileSignature|PFID|FIDO-Push for E-Finance")
  String getAccessMethod();

  @Schema(description = "Optional flag to force that the login type is changed. If the value is true at the login step 1 a list of possible login types is returned as a challenge object. The selected login type must be returned in the challenge field")
  Boolean getChangeAccessMethod();

}