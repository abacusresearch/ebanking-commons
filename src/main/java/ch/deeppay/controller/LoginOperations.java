package ch.deeppay.controller;

import ch.deeppay.models.login.LoginRequest;
import ch.deeppay.models.login.LoginResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Nonnull;

@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public interface LoginOperations {

  @PostMapping
  ResponseEntity<LoginResponse> login(@Nonnull @RequestHeader final HttpHeaders headers,
                                      @Nonnull @RequestBody final LoginRequest clientRequest);

}
