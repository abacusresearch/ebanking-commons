package ch.deeppay.controller;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.Instant;

import ch.deeppay.models.ebanking.statement.StatementRequest;
import ch.deeppay.models.ebanking.statement.StatementResponse;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.util.FileFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ch.deeppay.metrics.UserAgentConst.ABACUS_G4;
import static ch.deeppay.metrics.UserAgentConst.ABANINJA;
import static ch.deeppay.metrics.UserAgentConst.ABASALARY;
import static ch.deeppay.metrics.UserAgentConst.DEEPBOX;
import static ch.deeppay.metrics.UserAgentConst.POSTMAN;
import static ch.deeppay.metrics.UserAgentConst.SWISS21;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.BAD_GATEWAY_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.BAD_REQUEST_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.FORBIDDEN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.NO_CONTENT_STATEMENT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_STATEMENT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_STATEMENT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_STATEMENT_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.PATH_STATEMENT_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.PATH_STATEMENT_ID_EXAMPLE;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.PATH_STATEMENT_ID_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_ACCOUNT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_ACCOUNT_EXAMPLE;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_ACCOUNT_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_FROM_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_FROM_EXAMPLE;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_FROM_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_TO_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_TO_EXAMPLE;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_DATE_TO_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_FORMAT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_FORMAT_EXAMPLE;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_FORMAT_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_GATEWAY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_REQUEST;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_FORBIDDEN;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_NO_CONTENT;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_UNAUTHORIZED;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.UNAUTHORIZED_DESCRIPTION;

/**
 * Rest endpoints for downloading statements.
 */
@RestController
@RequestMapping(value = "/statements", produces = MediaType.APPLICATION_JSON_VALUE)
public interface StatementOperations {

  @Operation(summary = OPERATION_STATEMENT_SUMMARY, description = OPERATION_STATEMENT_DESCRIPTION, tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_STATEMENT_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(oneOf = {StatementResponse.class}))}),
      @ApiResponse(responseCode = RESPONSE_CODE_NO_CONTENT,
                   description = NO_CONTENT_STATEMENT_DESCRIPTION),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST,
                   description = BAD_REQUEST_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
                   description = UNAUTHORIZED_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_FORBIDDEN,
                   description = FORBIDDEN_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_GATEWAY,
                   description = BAD_GATEWAY_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, examples = {@ExampleObject(value = OpenApiBankingTextConst.BAD_REQUEST_EXAMPLE)}, schema = @Schema(implementation = OpenApiDeepPayProblem.class))})})
  @Parameter(in = ParameterIn.HEADER,
             required = true,
             name = HttpHeaders.USER_AGENT,
             schema = @Schema(allowableValues = {ABACUS_G4, ABANINJA, ABASALARY, POSTMAN, DEEPBOX, SWISS21},
                              example = OpenApiBankingTextConst.SCHEMA_CLIENT_TYPE_EXAMPLE))
  @Parameter(in = ParameterIn.COOKIE, required = true, name = HEADER_COOKIE_SESSION_TRACE_ID, description = HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION,schema = @Schema(implementation = String.class))
  @Parameter(in = ParameterIn.QUERY, required = true, name = QUERY_FORMAT_NAME, description = QUERY_FORMAT_DESCRIPTION, example = QUERY_FORMAT_EXAMPLE, schema = @Schema(implementation = FileFormat.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_DATE_FROM_NAME,description = QUERY_DATE_FROM_DESCRIPTION, example = QUERY_DATE_FROM_EXAMPLE, schema = @Schema(implementation = Instant.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_DATE_TO_NAME, description = QUERY_DATE_TO_DESCRIPTION, example = QUERY_DATE_TO_EXAMPLE,schema = @Schema(implementation = Instant.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_ACCOUNT_NAME, description = QUERY_ACCOUNT_DESCRIPTION, example = QUERY_ACCOUNT_EXAMPLE,schema = @Schema(type = "string"))
  @GetMapping
  ResponseEntity<StatementResponse> downloadStatements(
      @RequestHeader @NonNull final HttpHeaders httpHeaders,
      @Parameter(hidden = true, description = "Is hidden because Query parameters are defined above") final @Valid StatementRequest statementRequest);


  @Operation(summary = OPERATION_STATEMENT_SUMMARY, description = OPERATION_STATEMENT_DESCRIPTION, tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_STATEMENT_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(oneOf = {StatementResponse.class}))}),
      @ApiResponse(responseCode = RESPONSE_CODE_NO_CONTENT,
                   description = NO_CONTENT_STATEMENT_DESCRIPTION),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST,
                   description = BAD_REQUEST_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
                   description = UNAUTHORIZED_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_FORBIDDEN,
                   description = FORBIDDEN_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_GATEWAY,
                   description = BAD_GATEWAY_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, examples = {@ExampleObject(value = OpenApiBankingTextConst.BAD_REQUEST_EXAMPLE)}, schema = @Schema(implementation = OpenApiDeepPayProblem.class))})})
  @Parameter(in = ParameterIn.HEADER,
             required = true,
             name = HttpHeaders.USER_AGENT,
             schema = @Schema(allowableValues = {ABACUS_G4, ABANINJA, ABASALARY, POSTMAN, DEEPBOX, SWISS21},
                              example = OpenApiBankingTextConst.SCHEMA_CLIENT_TYPE_EXAMPLE))
  @Parameter(in = ParameterIn.COOKIE, required = true, name = HEADER_COOKIE_SESSION_TRACE_ID, description = HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION,schema = @Schema(type = "string"))
  @Parameter(in = ParameterIn.PATH,required = true, name = PATH_STATEMENT_ID_NAME, schema = @Schema(description = PATH_STATEMENT_ID_DESCRIPTION, example = PATH_STATEMENT_ID_EXAMPLE))
  @Parameter(in = ParameterIn.QUERY, required = true, name = QUERY_FORMAT_NAME, description = QUERY_FORMAT_DESCRIPTION, example = QUERY_FORMAT_EXAMPLE, schema = @Schema(implementation = FileFormat.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_DATE_FROM_NAME,description = QUERY_DATE_FROM_DESCRIPTION, example = QUERY_DATE_FROM_EXAMPLE, schema = @Schema(implementation = Instant.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_DATE_TO_NAME, description = QUERY_DATE_TO_DESCRIPTION, example = QUERY_DATE_TO_EXAMPLE,schema = @Schema(implementation = Instant.class))
  @Parameter(in = ParameterIn.QUERY, name = QUERY_ACCOUNT_NAME, description = QUERY_ACCOUNT_DESCRIPTION, example = QUERY_ACCOUNT_EXAMPLE,schema = @Schema(type = "string"))
  @GetMapping(value = "/{statementId}")
  ResponseEntity<StatementResponse> downloadStatement(
      @RequestHeader @NonNull final HttpHeaders httpHeaders,
      @PathVariable final @NotBlank(message = "StatementId must be set.") String statementId,
      @Parameter(hidden = true, description = "Is hidden because Query parameters are defined above") final @Valid StatementRequest statementRequest);

}
