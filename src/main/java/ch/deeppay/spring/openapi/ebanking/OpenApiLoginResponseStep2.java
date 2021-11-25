package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.util.LoginState;
import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name = "Step2LoginResponse", description = "Login response after step 2")
public interface OpenApiLoginResponseStep2 {

  @Schema(description = "The state contains 'SUCCESS' when the step 2 of the login flow was successfully", example = "SUCCESS", required = true)
  LoginState getState();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  String getTransportData();

}