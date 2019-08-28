package ch.deeppay.controller;

import ch.deeppay.models.statement.StatementRequest;
import ch.deeppay.models.statement.StatementResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest endpoints for downloading statements.
 */
@RestController
@RequestMapping(value = "/statements", produces = MediaType.APPLICATION_JSON_VALUE)
public interface StatementOperations {

  @GetMapping
  ResponseEntity<StatementResponse> downloadStatements(
      @RequestHeader final HttpHeaders headers,
      StatementRequest request);

  @GetMapping(value = "/{statementId}")
  ResponseEntity<StatementResponse> downloadStatement(
      @RequestHeader final HttpHeaders headers,
      @PathVariable final String statementId,
      StatementRequest request);

}
