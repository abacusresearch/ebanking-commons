package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

/**
 * Interface is only used for open api doc's
 */
@Schema(name= "RequestChallenge", description = "Challenge request for the login step 2")
public interface OpenApiLoginRequestChallenge {
  @Schema(description = "Challenge value that was entered by the user",required = true)
  String getChallenge();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  String getTransportData();

}