package ch.deeppay.controller;

import javax.validation.Valid;

import ch.deeppay.models.ebanking.login.LoginRequest;
import ch.deeppay.models.ebanking.login.LoginResponse;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestChallenge;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginRequestPassword;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep1;
import ch.deeppay.spring.openapi.ebanking.OpenApiLoginResponseStep2;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
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
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.BAD_GATEWAY_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.BAD_REQUEST_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.FORBIDDEN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_X_REQUEST_TRACE_ID;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_X_REQUEST_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_X_SESSION_TRACE_ID;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.HEADER_X_SESSION_TRACE_ID_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.LOGIN_REQUEST_BODY_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_LOGIN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_LOGIN_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_LOGIN_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_GATEWAY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_REQUEST;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_FORBIDDEN;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_UNAUTHORIZED;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_DESCRIPTION_E_BANKING;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.UNAUTHORIZED_DESCRIPTION;

@SecuritySchemes(value = {
    @SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "Bearer", bearerFormat = "JWT"),
    @SecurityScheme(type = SecuritySchemeType.APIKEY, name = "x-api-key")
})
//OpenAPIDefinition does not work in package-info.java class.
@OpenAPIDefinition(info = @Info(title = "Rest endpoints for e-banking service",
                                description = "This section describes all available endpoints of the e-banking rest interface"),
                   servers = {@Server(url = OpenApiBankingTextConst.SERVER_DEV_URL, description = OpenApiBankingTextConst.SERVER_DEV_URL_DESCRIPTION),
                              @Server(url = OpenApiBankingTextConst.SERVER_INT_URL, description = OpenApiBankingTextConst.SERVER_INT_URL_DESCRIPTION),
                              @Server(url = OpenApiBankingTextConst.SERVER_PROD_URL, description = OpenApiBankingTextConst.SERVER_PROD_URL_DESCRIPTION)},
                   tags = {@Tag(name = TAG_NAME_E_BANKING, description = TAG_DESCRIPTION_E_BANKING)})
@RestController
@RequestMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
public interface LoginOperations {

  @Operation(summary = OPERATION_LOGIN_SUMMARY,
             description = OPERATION_LOGIN_DESCRIPTION,
             tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_LOGIN_DESCRIPTION,
                   headers = {@Header(name = HEADER_X_REQUEST_TRACE_ID, description = HEADER_X_REQUEST_TRACE_ID_DESCRIPTION),
                              @Header(name = HEADER_X_SESSION_TRACE_ID, description = HEADER_X_SESSION_TRACE_ID_DESCRIPTION)},
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                       schema = @Schema(oneOf = {OpenApiLoginResponseStep1.class, OpenApiLoginResponseStep2.class}))}),
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
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE, schema = @Schema(implementation = OpenApiDeepPayProblem.class))})})
  @Parameter(in = ParameterIn.HEADER,
             required = true,
             name = HttpHeaders.USER_AGENT,
             schema = @Schema(allowableValues = {ABACUS_G4, ABANINJA, ABASALARY, POSTMAN, DEEPBOX, SWISS21},
                              example = OpenApiBankingTextConst.SCHEMA_CLIENT_TYPE_EXAMPLE))
  @Parameter(in = ParameterIn.COOKIE, required = true, name = HEADER_COOKIE_SESSION_TRACE_ID, description = HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION)
  @io.swagger.v3.oas.annotations.parameters.RequestBody(description = LOGIN_REQUEST_BODY_DESCRIPTION, required = true,
                                                        content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                                                                           schema = @Schema(oneOf = {OpenApiLoginRequestPassword.class,
                                                                                                     OpenApiLoginRequestChallenge.class})))
  @PostMapping
  ResponseEntity<LoginResponse> login(@RequestHeader @NonNull final HttpHeaders headers,
                                      @RequestBody @NonNull final @Valid LoginRequest clientRequest);

}
