package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.constraints.LanguageConstraint;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Interface is only used for open api doc's
 */
@Schema(name="RequestPassword", description = "Password login request for step1")
public interface OpenApiLoginRequestPassword {
  @Schema(description = "ContractId that is given by the financial institute e-banking contract",required = true, example = "700-1234-5678")
  String getContractId();

  @Schema(description = "Passwort of the contract", required = true,example = "1234567890")
  String getPassword();

  @Schema(description = "The bankId is the bank identification received by the discovery route. It is used to forward the request to the right bank. ",required = true,example = "8158")
  String getBankId();

  @LanguageConstraint
  @Schema(description = "Language that might be used for result message",required = true, example = "de")
  String getLanguage();

  @Schema(description = "Optional path to connect to an institution. If this parameter is omitted, the bankId parameter is used to identify the institution path.", example = "https://test.bank.ch")
  String getUrl();

}