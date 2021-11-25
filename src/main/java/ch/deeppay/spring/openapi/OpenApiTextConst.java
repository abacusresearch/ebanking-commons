package ch.deeppay.spring.openapi;

public interface OpenApiTextConst {

  String RESPONSE_CODE_OK = "200";
  String RESPONSE_CODE_NO_CONTENT = "204";

  String RESPONSE_CODE_BAD_REQUEST = "400";
  String BAD_REQUEST_DESCRIPTION = "Some parameters were invalid";
  String BAD_REQUEST_EXAMPLE = "{\"type\": \"https://docs.api.deeppay.swiss/ebanking/interfaces/invalid_parameter\",\"title\": \"Some of the passed parameters are invalid\",\"status\": 400,\"detail\": \"detail error message\",\"instance\": \"/path\",\"traceId\": \"50bd2fbb8c9c9833\",\"sessionTraceId\": \"03c34b94f6b8a686fff5e7047b25446c\"}";

  String RESPONSE_CODE_UNAUTHORIZED = "401";
  String UNAUTHORIZED_DESCRIPTION = "Not authorized";

  String RESPONSE_CODE_FORBIDDEN = "403";
  String FORBIDDEN_DESCRIPTION = "Missing access rights";

  String RESPONSE_CODE_BAD_GATEWAY = "502";
  String BAD_GATEWAY_DESCRIPTION = "API method from the Institute has returned an error.";

  String OPERATION_HEALTH_SUMMARY = "Method to get the health status of the current service.";

  String SERVER_DEV_URL = "https://api.dev.deeppay.swiss";
  String SERVER_DEV_URL_DESCRIPTION = "Base url to development environment (for internal tests only)";

  String SERVER_INT_URL = "https://api.int.deeppay.swiss";
  String SERVER_INT_URL_DESCRIPTION = "Base url to integration environment";

  String SERVER_PROD_URL = "https://api.deeppay.swiss";
  String SERVER_PROD_URL_DESCRIPTION = "Base url to production environment";

  String HEADER_X_REQUEST_TRACE_ID = "x-request-trace-id";
  String HEADER_X_REQUEST_TRACE_ID_DESCRIPTION = "Trace id for a single request";
  String HEADER_X_SESSION_TRACE_ID = "x-session-trace-id";
  String HEADER_X_SESSION_TRACE_ID_DESCRIPTION = "Trace id for a session";

  String HEADER_COOKIE_SESSION_TRACE_ID = "DeepPaySessionTraceId";
  String HEADER_COOKIE_SESSION_TRACE_ID_DESCRIPTION = "This cookie is used for session tracing";
}
