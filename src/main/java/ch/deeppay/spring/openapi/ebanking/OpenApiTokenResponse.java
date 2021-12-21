package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_REFRESH_TOKEN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name = "AuthorizeRequest", description = "Login response after the first login step")
public interface OpenApiTokenResponse {

  @Schema(description = "The access token is used to get access to the protected resource", required = true)
  String getAccessToken();

  @Schema(description = "Type of the token", example = "bearer", required = true)
  String getTokenType();

  @Schema(description = SCHEMA_REFRESH_TOKEN_DESCRIPTION, required = true)
  String getRefreshToken();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, example = "6300", required = true)
  String getTransportData();


}