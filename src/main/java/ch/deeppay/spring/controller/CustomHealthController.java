package ch.deeppay.spring.controller;

import javax.annotation.Nonnull;
import java.net.UnknownHostException;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_HEALTH_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_HEALTH_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;

/**
 * This is controller for a custom health point. We run the actuator routes on a different port.
 * Therefore we cannot reach the actuator health on the server port.
 * But it should be possible to check whether the services can be reached via the Api-Gateway.
 */
@RestController
@ConditionalOnProperty(value = "ch.deeppay.spring.custom-health-controller.enabled", matchIfMissing = true)
public class CustomHealthController {

  private final HealthEndpoint healthEndpoint;

  public CustomHealthController(@Nonnull final HealthEndpoint healthEndpoint) {
    this.healthEndpoint = healthEndpoint;
  }

  @Operation(summary = OPERATION_HEALTH_SUMMARY, tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_HEALTH_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = Status.class))})})
  @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
  public Status test() throws UnknownHostException {
    return healthEndpoint.health().getStatus();
  }
}
