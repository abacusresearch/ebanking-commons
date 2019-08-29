package ch.deeppay.controller;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.models.logout.LogoutResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest endpoints for logout.
 */
@RestController
@RequestMapping(value = "/variations/standard/logout", produces = "application/json")
public interface LogoutOperations {

  @PostMapping
  public ResponseEntity<LogoutResponse> logout(@RequestHeader final HttpHeaders headers,
                                               @RequestBody final ClientRequest clientRequest);

}
