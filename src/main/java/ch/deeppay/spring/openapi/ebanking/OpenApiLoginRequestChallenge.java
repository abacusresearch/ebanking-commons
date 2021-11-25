package ch.deeppay.spring.openapi.ebanking;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Interface is only used for open api doc's
 */
@Schema(name= "RequestChallenge", description = "Challenge request for the login step 2")
public interface OpenApiLoginRequestChallenge {
  @Schema(description = "Challenge value that was entered by the user",required = true)
  String getChallenge();

  @Schema(description = "Transportdata that contains all session information",required = true)
  String getTransportData();

}