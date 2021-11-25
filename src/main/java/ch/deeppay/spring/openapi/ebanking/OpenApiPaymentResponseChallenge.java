package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.models.ebanking.login.Challenge;
import ch.deeppay.util.LoginState;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name="ConfirmPaymentResponse", description = "The response of an uploaded payment con ")
public interface OpenApiPaymentResponseChallenge {

  @Schema(description = "State contains the confirm type of the payment. The process is analog to the login step1 flow.", example = "PHOTOTAN", required = true)
  LoginState getState();

  @Schema(description = "Might contain a challenge object (e.g an image of a photo tan).")
  Challenge getChallenge();

  @Schema(description = OpenApiTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION,required = true)
  String getTransportData();


}