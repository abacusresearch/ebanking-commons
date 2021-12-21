package ch.deeppay.controller;

import javax.validation.Valid;

import ch.deeppay.models.ebanking.payment.PaymentRequest;
import ch.deeppay.models.ebanking.payment.PaymentResponse;
import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import ch.deeppay.spring.openapi.ebanking.OpenAPiPaymentChallengeRequest;
import ch.deeppay.spring.openapi.ebanking.OpenAPiPaymentRequest;
import ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst;
import ch.deeppay.spring.openapi.ebanking.OpenApiPaymentResponseChallenge;
import ch.deeppay.spring.openapi.ebanking.OpenApiPaymentResponseSimple;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OK_PAYMENT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_PAYMENT_DESCRIPTION;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.OPERATION_PAYMENT_SUMMARY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_GATEWAY;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_BAD_REQUEST;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_FORBIDDEN;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_OK;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.RESPONSE_CODE_UNAUTHORIZED;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.TAG_NAME_E_BANKING;
import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.UNAUTHORIZED_DESCRIPTION;

/**
 * Rest endpoints for uploading payment files.
 */
@RestController
@RequestMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PaymentOperations {

  @Operation(summary = OPERATION_PAYMENT_SUMMARY, description = OPERATION_PAYMENT_DESCRIPTION, tags = {TAG_NAME_E_BANKING})
  @ApiResponses(value = {
      @ApiResponse(responseCode = RESPONSE_CODE_OK,
                   description = OK_PAYMENT_DESCRIPTION,
                   content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(oneOf = {OpenApiPaymentResponseSimple.class,
                                                                                                               OpenApiPaymentResponseChallenge.class}))}),
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
                   content = {@Content(mediaType = MediaType.APPLICATION_PROBLEM_JSON_VALUE,
                                       examples = {@ExampleObject(value = OpenApiBankingTextConst.BAD_REQUEST_EXAMPLE)},
                                       schema = @Schema(implementation = OpenApiDeepPayProblem.class))})})
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
  @RequestBody(required = true,
               content = @Content(mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                                  schema = @Schema(oneOf = {OpenAPiPaymentRequest.class, OpenAPiPaymentChallengeRequest.class})))

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<PaymentResponse> uploadPayment(@RequestHeader @NonNull final HttpHeaders httpHeaders,
                                                @RequestParam("file") @Nullable final MultipartFile file,
                                                final @Valid PaymentRequest paymentRequest);
}
