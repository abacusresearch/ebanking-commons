package ch.deeppay.spring.controller;

import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * This is controller for a custom health point. We run the actuator routes on a different port.
 * Therefore we cannot reach the actuator health on the server port.
 * But it should be possible to check whether the services can be reached via the Api-Gateway.
 */
@RestController
public class CustomHealthController {

  private final Environment environment;

  public CustomHealthController(@Nonnull final Environment environment) {
    this.environment = environment;
  }

  @GetMapping(value = "/health", produces = MediaType.APPLICATION_JSON_VALUE)
  public String test() throws UnknownHostException {
    String port = getPort();

    String actuatorHealthRoute = "http://" + InetAddress.getLocalHost().getHostAddress() + ":" + port + "/actuator/health";

    return new RestTemplate()
        .getForObject(actuatorHealthRoute, String.class);
  }

  private String getPort() {
    String port = environment.getProperty("management.server.port");
    if (port == null || "0".equals(port)) {
      return environment.getProperty("server.port");
    }
    return port;
  }
}
