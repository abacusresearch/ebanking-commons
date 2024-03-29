package ch.deeppay.exception;

import org.springframework.http.HttpStatus;

import java.net.URI;

public enum DeepPayProblemType implements DeepPayProblemTypeGetter {

  INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED,
                            "Entered login credentials are invalid",
                            "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_login_credentials"),
  BLOCKED_CONTRACT(HttpStatus.FORBIDDEN, "Contract is blocked", "https://docs.api.deeppay.swiss/ebanking/interfaces/blocked_contract"),
  LOGIN_FAIlED(HttpStatus.UNAUTHORIZED, "Login has failed", "https://docs.api.deeppay.swiss/ebanking/interfaces/login_failed"),
  INVALID_SESSION(HttpStatus.UNAUTHORIZED, "Session is invalid", "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_session"),
  REQUEST_REJECTED(HttpStatus.UNAUTHORIZED,
                   "Request was rejected",
                   "https://docs.api.deeppay.swiss/ebanking/interfaces/request_rejected"),
  PASSWORD_CHANGE_REQUIRED(HttpStatus.UNAUTHORIZED,
                           "Password has to be changed",
                           "https://docs.api.deeppay.swiss/ebanking/interfaces/password_change_required"),
  INVALID_PARAMETER(HttpStatus.BAD_REQUEST, "Invalid parameter values", "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_parameter"),
  OPERATION_NOT_SUPPORTED(HttpStatus.UNAUTHORIZED,
                          "Operation is not supported by this contract",
                          "https://docs.api.deeppay.swiss/ebanking/interfaces/operation_not_supported"),
  MISSING_ACCESS_RIGHTS(HttpStatus.FORBIDDEN,
                        "The contract does not has the appropriate access rights",
                        "https://docs.api.deeppay.swiss/ebanking/interfaces/missing_access_rights"),
  INVALID_CONTENT(HttpStatus.BAD_REQUEST,
                  "The content of the request is invalid",
                  "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_content"), //TODO prüfen ob titel kürzer sein soll
  NO_DATA_AVAILABLE(HttpStatus.NOT_FOUND, "No data are available", "https://docs.api.deeppay.swiss/ebanking/interfaces/no_data_available"),
  DUPLICATE_FILE(HttpStatus.BAD_REQUEST,
                 "File already exists",
                 "https://docs.api.deeppay.swiss/ebanking/interfaces/duplicate_file"),
  VALIDATION_IN_PROGRESS(HttpStatus.NOT_FOUND,
                         "Validation is in progress",
                         "https://docs.api.deeppay.swiss/ebanking/interfaces/validation_in_progress"),
  INSTITUTE_SERVICE_UNAVAILABLE(HttpStatus.SERVICE_UNAVAILABLE,
                                "Service of the institute is unavailable",
                                "https://docs.api.deeppay.swiss/ebanking/interfaces/institute_service_unavailable"),
  INSTITUTE_API_ERROR(HttpStatus.BAD_GATEWAY,
                      "Institute api error",
                      "https://docs.api.deeppay.swiss/ebanking/interfaces/institute_api_error"),
  INVALID_GATEWAY_AUTHORIZATION(HttpStatus.UNAUTHORIZED,
                                "Unauthorized api call",
                                "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_gateway_authorization"),
  CONTRACT_NOT_FOUND(HttpStatus.NOT_FOUND,
                     "Contract not found",
                     "https://docs.api.deeppay.swiss/ebanking/interfaces/contract_not_found"),
  CONTRACT_NOT_ACTIVE(HttpStatus.FORBIDDEN,
                      "Contract not active",
                      "https://docs.api.deeppay.swiss/ebanking/interfaces/contract_not_active"),
  CREDIT_CARD_NOT_FOUND(HttpStatus.NOT_FOUND,
                        "Credit card not found",
                        "https://docs.api.deeppay.swiss/ebanking/interfaces/credit_card_not_found"),
  CREDIT_CARD_NOT_ACTIVE(HttpStatus.FORBIDDEN,
                         "Credit card is not active",
                         "https://docs.api.deeppay.swiss/ebanking/interfaces/credit_card_not_active"),
  CREDIT_CARD_BLOCKED(HttpStatus.FORBIDDEN,
                      "Credit card is blocked",
                      "https://docs.api.deeppay.swiss/ebanking/interfaces/credit_card_blocked"),
  CREDIT_CARD_EXPIRED(HttpStatus.FORBIDDEN,
                      "Credit card is expired",
                      "https://docs.api.deeppay.swiss/ebanking/interfaces/credit_card_expired");

  private final HttpStatus httpStatus;
  private final String title;
  private final URI uri;

  DeepPayProblemType(final HttpStatus httpStatus, final String title, final String uri) {
    this.httpStatus = httpStatus;
    this.title = title;
    this.uri = URI.create(uri);
  }

  @Override
  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  @Override
  public String getTitle() {
    return title;
  }

  @Override
  public URI getUri() {
    return uri;
  }
}