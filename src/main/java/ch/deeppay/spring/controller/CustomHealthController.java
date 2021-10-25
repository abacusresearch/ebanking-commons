package ch.deeppay.spring.controller;

import javax.annotation.Nonnull;
import java.net.UnknownHostException;

import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.health.Status;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
  public Status test() throws UnknownHostException {
    return healthEndpoint.health().getStatus();
  }
}
