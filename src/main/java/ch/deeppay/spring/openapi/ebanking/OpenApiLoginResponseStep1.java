package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.models.ebanking.login.Challenge;
import ch.deeppay.util.LoginState;
import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_CHALLENGE_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name = "Step1LoginResponse", description = "Login response after the first login step")
public interface OpenApiLoginResponseStep1 {

  @Schema(description = "The state contains the confirmation type of the login step 2", example = "PHOTOTAN", required = true)
  LoginState getState();

  @Schema(description = SCHEMA_CHALLENGE_DESCRIPTION)
  Challenge getChallenge();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  String getTransportData();

}