package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.models.ebanking.login.Challenge;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Interface is only used for open api doc's
 */
@Schema(name= "RequestChallenge", description = "Challenge request for the login step 2")
public interface OpenApiLoginRequestChallenge {
  @Schema(description = "Challenge value that was entered by the user",required = true)
  Challenge getChallenge();

  @Schema(description = "Transportdata that contains all session information",required = true)
  String getTransportData();

}