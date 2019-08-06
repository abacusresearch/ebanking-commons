package ch.deeppay.controller;

import ch.deeppay.models.statement.StatementResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Rest endpoints for downloading statements.
 */
@RestController
@RequestMapping(value = "/statements", produces = MediaType.APPLICATION_JSON_VALUE)
public interface StatementOperations {

  @GetMapping
  ResponseEntity<StatementResponse> downloadStatements(
      @RequestHeader final HttpHeaders headers,
      @RequestParam final String transportData,
      @RequestParam final String format,
      @RequestParam(required = false) final String account,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date dateFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date dateTo);

  @GetMapping(value = "/{statementId}")
  public ResponseEntity<StatementResponse> downloadStatement(
      @RequestHeader final HttpHeaders headers,
      @PathVariable final String statementId,
      @RequestParam final String transportData,
      @RequestParam final String format,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date dateFrom,
      @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) final Date dateTo);
}
