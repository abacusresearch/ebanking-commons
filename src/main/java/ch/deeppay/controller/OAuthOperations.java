package ch.deeppay.controller;

import ch.deeppay.models.ebanking.oauth.AuthorizeRequest;
import ch.deeppay.models.ebanking.oauth.TokenRequest;
import ch.deeppay.models.ebanking.oauth.TokenResponse;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.spring.openapi.ebanking.OpenApiAuthorizeRequest;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep1;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep2;
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
                                       schema = @Schema(oneOf = {OpenApiLoginResponseStep1.class, OpenApiLoginResponseStep2.class}))}),
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
                                                                           schema = @Schema(implementation = OpenApiAuthorizeRequest.class)))
  @GetMapping(path = "/authorize")
  ModelAndView handleAuthorize(@NonNull final AuthorizeRequest oAuthAuthorizeRequest);

  /**
   * @param grandType    The grant type of the request, must be refresh_token when refreshing an access token.
   *                     Must be authorization_code for the initial request to get the access and refresh tokens.
   * @param code         The code parameter returned to your redirect URI when the user authorized your app.
   * @param clientId     The Client ID of your app.
   * @param clientSecret The Client Secret of your app.
   * @param refreshToken The refresh token obtained when initially authenticating your OAuth integration.
   * @param redirectUrl  The redirect URI that was used when the user authorized your app. This must exactly match the redirect_uri used when intiating the OAuth 2.0 connection.
   * @return TokenResponse
   */

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
