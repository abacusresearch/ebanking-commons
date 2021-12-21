package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_BANK_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_LANGUAGE_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_REFRESH_TOKEN_DESCRIPTION;

@Schema(name = "TokenRequest", description = "Request data to get new tokens")
public interface OpenApiTokenRequest {

  @Schema(description = SCHEMA_BANK_ID_DESCRIPTION, example = "6300", required = true)
  String getBankId();

  @Schema(description = SCHEMA_LANGUAGE_DESCRIPTION, example = "de", required = true)
  String getLanguage();

  @Schema(description = "The grant type of the request, must be refreshToken when refreshing an access token and must be authorizationCode for the initial request to get the access and refresh tokens.", example = "refreshToken", required = true)
  String getGrantType();

  @Schema(description = "The code parameter returned to your redirect URI when the user authorized your app", example = "adcfdse", required = true)
  String getCode();

  @Schema(description = "The Identifier of the application that asks for authorization.", example = "0oa2hl2inow5Uqc6c357", required = true)
  String getClientId();

  @Schema(description = "The client secret it's like a password. it's a secret known only to the application and the authorization server.", example = "269d98e4922fb3895e9ae2108cbb5064", required = true)
  String getClientSecret();

  @Schema(description = SCHEMA_REFRESH_TOKEN_DESCRIPTION, required = true)
  String getRefreshToken();

  @Schema(description = "The redirect URI that was used when the user authorized the application. This must exactly match the redirect_uri used when initiating the oauth 2.0 connection.", required = true)
  String getRedirectUrl();



}