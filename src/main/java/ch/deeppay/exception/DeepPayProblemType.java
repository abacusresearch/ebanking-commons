package ch.deeppay.exception;


import java.net.URI;

import org.springframework.http.HttpStatus;

public enum DeepPayProblemType implements DeepPayProblemTypeGetter {

  INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED,
                            "Entered login credentials are invalid",
                            "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_login_credentials"),
  BLOCKED_CONTRACT(HttpStatus.FORBIDDEN, "Contract is blocked", "https://docs.api.deeppay.swiss/ebanking/interfaces/blocked_contract"),
  LOGIN_FAIlED(HttpStatus.UNAUTHORIZED, "Login has failed", "https://docs.api.deeppay.swiss/ebanking/interfaces/login_failed"),
  INVALID_SESSION(HttpStatus.UNAUTHORIZED, "Session is invalid", "https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_session"),
  REQUEST_REJECTED_BY_USER(HttpStatus.UNAUTHORIZED,
                           "Request was rejected by the user",
                           "https://docs.api.deeppay.swiss/ebanking/interfaces/request_rejected_by_user"),
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
  UNEXPECTED_API_ANSWER(HttpStatus.INTERNAL_SERVER_ERROR,
                        "Unexpected answer of the institute api call",
                        "https://docs.api.deeppay.swiss/ebanking/interfaces/unexpected_api_answer"),
  INSTITUTE_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR,
                         "Server error at the institute api call",
                         "https://docs.api.deeppay.swiss/ebanking/interfaces/institute_server_error");

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