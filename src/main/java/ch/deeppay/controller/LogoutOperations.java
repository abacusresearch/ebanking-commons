package ch.deeppay.controller;

import javax.validation.Valid;

import ch.deeppay.models.ebanking.logout.LogoutRequest;
import ch.deeppay.models.ebanking.logout.LogoutResponse;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static ch.deeppay.metrics.UserAgentConst.ABACUS_G4;
import static ch.deeppay.metrics.UserAgentConst.ABANINJA;
import static ch.deeppay.metrics.UserAgentConst.ABASALARY;
import static ch.deeppay.metrics.UserAgentConst.DEEPBOX;
import static ch.deeppay.metrics.UserAgentConst.POSTMAN;
import static ch.deeppay.metrics.UserAgentConst.SWISS21;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.BAD_REQUEST_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_LOGOUT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_LOGOUT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_LOGOUT_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_REQUEST;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_UNAUTHORIZED;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.UNAUTHORIZED_DESCRIPTION;

/**
 * Rest endpoints for logout.
 */
@RestController
@RequestMapping(value = "/logout", produces = MediaType.APPLICATION_JSON_VALUE)
public interface LogoutOperations {

  @Operation(summary = OPERATION_LOGOUT_SUMMARY, description = OPERATION_LOGOUT_DESCRIPTION, tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_LOGOUT_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = LogoutResponse.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST,
                   description = BAD_REQUEST_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
                   description = UNAUTHORIZED_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))})})
  @Parameter(in = ParameterIn.HEADER,
             required = true,
             name = HttpHeaders.USER_AGENT,
             schema = @Schema(allowableValues = {ABACUS_G4, ABANINJA, ABASALARY, POSTMAN, DEEPBOX, SWISS21},
                              example = OpenApiBankingTextConst.SCHEMA_CLIENT_TYPE_EXAMPLE))
  @Parameter(in = ParameterIn.COOKIE,
             required = true,
             name = HEADER_COOKIE_SESSION_TRACE_ID,
             description = HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION,
             schema = @Schema(type = "string"))
  @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                                                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                                           schema = @Schema(implementation = LogoutRequest.class)))

  @PostMapping
  ResponseEntity<LogoutResponse> logout(@RequestHeader @NonNull final HttpHeaders headers,
                                        @RequestBody @NonNull final @Valid LogoutRequest logoutRequest);

}

