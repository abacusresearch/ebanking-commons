package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_BANK_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_CLIENT_ID_DESCRIPTION;

@Schema(name = "AuthorizeRequest", description = "Login response after the first login step")
public interface OpenApiAuthorizeRequest {

  @Schema(description = SCHEMA_BANK_ID_DESCRIPTION, example = "6300", required = true)
  String getBankId();

  @Schema(description = SCHEMA_CLIENT_ID_DESCRIPTION, example = "abaninja", required = true)
  String getClientId();

  @Schema(description = "An opaque value, used for security purposes. Can be used to retrieve information on the clients application." , required = true)
  String getState();

  @Schema(description = "The URL to which the Server will send (through a redirect) the Authorization code, after the user login.", required = true)
  String getRedirectUrl();

}