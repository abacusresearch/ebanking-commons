package ch.deeppay.controller;

import ch.deeppay.models.ebanking.oauth.AuthorizeRequest;
import ch.deeppay.models.ebanking.oauth.TokenRequest;
import ch.deeppay.models.ebanking.oauth.TokenResponse;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import ch.deeppay.spring.openapi.ebanking.OpenApiTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import static ch.deeppay.metrics.UserAgentConst.ABACUS_G4;
import static ch.deeppay.metrics.UserAgentConst.ABANINJA;
import static ch.deeppay.metrics.UserAgentConst.ABASALARY;
import static ch.deeppay.metrics.UserAgentConst.DEEPBOX;
import static ch.deeppay.metrics.UserAgentConst.POSTMAN;
import static ch.deeppay.metrics.UserAgentConst.SWISS21;
import static ch.deeppay.spring.openapi.OpenApiTextConst.BAD_GATEWAY_DESCRIPTION;
import static ch.deeppay.spring.openapi.OpenApiTextConst.BAD_REQUEST_DESCRIPTION;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_COOKIE_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_X_REQUEST_TRACE_ID;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_X_REQUEST_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_X_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.OpenApiTextConst.HEADER_X_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.OpenApiTextConst.RESPONSE_CODE_BAD_GATEWAY;
import static ch.deeppay.spring.openapi.OpenApiTextConst.RESPONSE_CODE_BAD_REQUEST;
import static ch.deeppay.spring.openapi.OpenApiTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.OpenApiTextConst.RESPONSE_CODE_REDIRECT;
import static ch.deeppay.spring.openapi.OpenApiTextConst.RESPONSE_CODE_UNAUTHORIZED;
import static ch.deeppay.spring.openapi.OpenApiTextConst.UNAUTHORIZED_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.AUTHORIZE_REQUEST_BODY_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_TOKEN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_AUTHORIZE_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_AUTHORIZE_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_TOKEN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_TOKEN_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_BANK_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_BANK_ID_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_CLIENT_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_CLIENT_ID_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_REDIRECT_URL_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_REDIRECT_URL_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_STATE_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.QUERY_STATE_NAME;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_AUTHORIZE_REDIRECT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;

@RestController
@RequestMapping(value = "/oauth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface OAuthOperations {

  @Operation(summary = OPERATION_AUTHORIZE_SUMMARY,
             description = OPERATION_AUTHORIZE_DESCRIPTION,
             tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_REDIRECT,
                   description = RESPONSE_AUTHORIZE_REDIRECT_DESCRIPTION,
                   headers = {@Header(name = HEADER_X_REQUEST_TRACE_ID, description = HEADER_X_REQUEST_TRACE_ID_DESCRIPTION, schema = @Schema(type = "string")),
                              @Header(name = HEADER_X_SESSION_TRACE_ID,
                                      description = HEADER_X_SESSION_TRACE_ID_DESCRIPTION,
                                      schema = @Schema(type = "string"))},
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = ModelAndView.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST,
                   description = BAD_REQUEST_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
                   description = UNAUTHORIZED_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_GATEWAY,
                   description = BAD_GATEWAY_DESCRIPTION,
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
  @Parameter(in = ParameterIn.QUERY,
             required = true,
             name = QUERY_BANK_ID_NAME,
             description = QUERY_BANK_ID_DESCRIPTION,
             example = "6300",
             schema = @Schema(type = "string"))
  @Parameter(in = ParameterIn.QUERY,
             required = true,
             name = QUERY_CLIENT_ID_NAME,
             description = QUERY_CLIENT_ID_DESCRIPTION,
             example = "abaninja",
             schema = @Schema(type = "string"))
  @Parameter(in = ParameterIn.QUERY,
             required = true,
             name = QUERY_STATE_NAME,
             description = QUERY_STATE_DESCRIPTION,
             example = "2ac3eb63-328c-4ac0-a1b4-27f5c8a2543d",
             schema = @Schema(type = "string"))
  @Parameter(in = ParameterIn.QUERY,
             required = true,
             name = QUERY_REDIRECT_URL_NAME,
             description = QUERY_REDIRECT_URL_DESCRIPTION,
             example = "https://app.abaninja.ch/finances/banking/authorization",
             schema = @Schema(type = "string"))
  @GetMapping(path = "/authorize")
  ModelAndView handleAuthorize(@NonNull final AuthorizeRequest oAuthAuthorizeRequest);


  @Operation(summary = OPERATION_TOKEN_SUMMARY,
             description = OPERATION_TOKEN_DESCRIPTION,
             tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_TOKEN_DESCRIPTION,
                   headers = {@Header(name = HEADER_X_REQUEST_TRACE_ID, description = HEADER_X_REQUEST_TRACE_ID_DESCRIPTION, schema = @Schema(type = "string")),
                              @Header(name = HEADER_X_SESSION_TRACE_ID,
                                      description = HEADER_X_SESSION_TRACE_ID_DESCRIPTION,
                                      schema = @Schema(type = "string"))},
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(implementation = OpenApiTokenResponse.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_REQUEST,
                   description = BAD_REQUEST_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_UNAUTHORIZED,
                   description = UNAUTHORIZED_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))}),
      @ApiResponse(responseCode = RESPONSE_CODE_BAD_GATEWAY,
                   description = BAD_GATEWAY_DESCRIPTION,
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
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = AUTHORIZE_REQUEST_BODY_DESCRIPTION, required = true,
                                                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                                           schema = @Schema(implementation = AuthorizeRequest.class)))
  @PostMapping(path = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  ResponseEntity<TokenResponse> handleToken(@NonNull TokenRequest tokenRequest);

}
