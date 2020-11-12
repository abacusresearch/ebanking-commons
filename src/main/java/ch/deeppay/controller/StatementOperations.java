package ch.deeppay.controller;

import ch.deeppay.models.ebanking.statement.StatementRequest;
import ch.deeppay.models.ebanking.statement.StatementResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * Rest endpoints for downloading statements.
 */
@RestController
@RequestMapping(value = "/statements", produces = MediaType.APPLICATION_JSON_VALUE)
public interface StatementOperations {

  @GetMapping
  ResponseEntity<StatementResponse> downloadStatements(
      @RequestHeader @NonNull final HttpHeaders httpHeaders,
      final @Valid StatementRequest statementRequest);

  @GetMapping(value = "/{statementId}")
  ResponseEntity<StatementResponse> downloadStatement(
      @RequestHeader @NonNull final HttpHeaders httpHeaders,
      @PathVariable final @NotBlank(message = "StatementId must be set.") String statementId,
      final @Valid StatementRequest statementRequest);

}
