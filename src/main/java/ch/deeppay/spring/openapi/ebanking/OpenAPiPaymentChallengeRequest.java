package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name = "PaymentRequest", description = "Request to submit the challenge value to confirm the payment")
public interface OpenAPiPaymentChallengeRequest {

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  String getTransportData();

  @Nullable
  @Schema(description = "Challenge value to confirm a payment after it was uploaded.")
  String getChallenge();


}
