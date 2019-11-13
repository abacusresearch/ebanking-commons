package ch.deeppay.controller;

import ch.deeppay.models.ClientRequest;
import ch.deeppay.models.logout.LogoutResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * Rest endpoints for logout.
 */
@RestController
@RequestMapping(value = "/logout", produces = "application/json")
public interface LogoutOperations {

  @PostMapping
  ResponseEntity<LogoutResponse> logout(@RequestHeader @NonNull final HttpHeaders headers,
                                        @RequestBody @NonNull final @Valid ClientRequest clientRequest);

}

