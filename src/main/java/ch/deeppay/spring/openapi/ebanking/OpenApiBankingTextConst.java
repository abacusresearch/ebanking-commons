package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.openapi.OpenApiTextConst;

public interface OpenApiBankingTextConst extends OpenApiTextConst {

  String OK_LOGIN_DESCRIPTION = "Login step was successfully.";
  String OK_PAYMENT_DESCRIPTION = "Payment was successfully uploaded. The response can contain a pain002 file and a message id that can be used to retrieve a status update of the payment.";
  String OK_LOGOUT_DESCRIPTION = "Logout was successfully.";
  String OK_STATEMENT_DESCRIPTION = "Statement file exists.";
  String OK_HEALTH_DESCRIPTION = "Health status of the service.";

  String NO_CONTENT_STATEMENT_DESCRIPTION = "No account statements exists.";

  String TAG_NAME_E_BANKING = "E-Banking";
  String TAG_DESCRIPTION_E_BANKING = "Uniform interface to all Swiss financial institutions.";

  String OPERATION_LOGIN_SUMMARY = "Method to perform the login at the institute. The login might have 2 steps that has to be performed.";
  String OPERATION_LOGIN_DESCRIPTION = "This route is the starting point of the login flow. After each request to this route the state parameter of the response must be reacted to.\nHow to react to this parameter is described in the following See [Login flow](#/loginFlow).";

  String OPERATION_PAYMENT_SUMMARY = "Method to upload a payment file in Pain001 format.";
  String OPERATION_PAYMENT_DESCRIPTION = "This route is used to upload payment files. The format of the file must be defined according to the pain001 specification. The file may contain one or more payments and can be uploaded as plain text. The file is zipped and base64 encoded.";

  String OPERATION_LOGOUT_SUMMARY = "Method to perform a logout at the institute.";
  String OPERATION_LOGOUT_DESCRIPTION = "This route is used to logout the current session at the institute. The transport data can not be used anymore after logout was called.";

  String OPERATION_STATEMENT_SUMMARY = "Method to download an accounting file.";
  String OPERATION_STATEMENT_DESCRIPTION = "This route is used to download a file. The returned file content format depends on the format request parameter that is passed. The file is zipped and base64 encoded.";

  String LOGIN_REQUEST_BODY_DESCRIPTION = "Login request";

  String SCHEMA_TRANSPORT_DATA_DESCRIPTION = "The transport data value contains session information and must be passed with the next request.";
  String SCHEMA_CHALLENGE_DESCRIPTION = "Might contain a challenge object (e.g an image of a photo tan).";
  String SCHEMA_CLIENT_TYPE_EXAMPLE = "Abacus-G4";

  String QUERY_FORMAT_NAME = "format";
  String QUERY_FORMAT_DESCRIPTION = "File format of the statement that has to be downloaded";
  String QUERY_FORMAT_EXAMPLE = "PAIN002";

  String QUERY_DATE_FROM_NAME = "dateFrom";
  String QUERY_DATE_FROM_DESCRIPTION = "Date range from (If no from date is set only not already downloaded files are considered)";
  String QUERY_DATE_FROM_EXAMPLE = "2021-01-01T10:05:15.953Z";

  String QUERY_DATE_TO_NAME = "dateTo";
  String QUERY_DATE_TO_DESCRIPTION = "Date range to (If no to date is set only not already downloaded files are considered)";
  String QUERY_DATE_TO_EXAMPLE = "2021-12-01T10:05:15.953Z";

  String QUERY_ACCOUNT_NAME = "account";
  String QUERY_ACCOUNT_DESCRIPTION = "Account number (If no account number is set data from all accounts that are linked to this contract are considered)";
  String QUERY_ACCOUNT_EXAMPLE = "CH1234000016259568011";

  String PATH_STATEMENT_ID_NAME = "statementId";
  String PATH_STATEMENT_ID_DESCRIPTION = "Reference id of a payment";
  String PATH_STATEMENT_ID_EXAMPLE = "asd324fgasdt6436lbs";

}