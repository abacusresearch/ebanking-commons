package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.spring.openapi.OpenApiTextConst;

public interface OpenApiBankingTextConst extends OpenApiTextConst {

  String OK_LOGIN_DESCRIPTION = "Login step was successfully.";
  String OK_PAYMENT_DESCRIPTION = "Payment was successfully uploaded. The response can contain a pain002 file and a message id that can be used to retrieve a status update of the payment.";
  String OK_LOGOUT_DESCRIPTION = "Logout was successfully.";
  String OK_STATEMENT_DESCRIPTION = "Statement file exists.";
  String OK_HEALTH_DESCRIPTION = "Health status of the service.";
  String OK_TOKEN_DESCRIPTION = "Tokens successfully retrieved.";

  String NO_CONTENT_STATEMENT_DESCRIPTION = "No account statements exists.";

  String TAG_NAME_E_BANKING = "E-Banking";
  String TAG_DESCRIPTION_E_BANKING = "Uniform interface to all Swiss financial institutions.";

  String OPERATION_LOGIN_SUMMARY = "Method to perform the login at the institute. The login might have 2 steps that has to be performed.";
  String OPERATION_LOGIN_DESCRIPTION = "This route is the starting point of the login flow. After each request to this route the state parameter of the response must be reacted to." +
                                       "For the most services the login flow consists of 2 steps. The first step usually involves entering a contract id and a password. The login method of the 2 step varies according to the contract and the institute." +
                                       "The type of the login methode is returned in the response of the login step 1.\n" +
                                       "### Possible login states after step 1\n" +
                                       "*Note: Login types must be handled on client side. The same states (except CHANGE_PASSWORD) might also be returned when a payment has to be confirmed*\n" +
                                       "\n" +
                                       "| Login state         | Description |\n" +
                                       "| ------------------- | ------------|\n" +
                                       "| MOBILE_TAN          | Is a tan that was sent by the institute to the registered mobile device. The user has to enter to tan into a login field and the value has be send back.|\n" +
                                       "| PUSH_TAN            | The tan is sent in the challenge field. The same tan value is sent by the institute to a registered mobile device. The user has to compare the values and confirm it on the mobile device|\n" +
                                       "| GRID_CARD           | The text is found under the key 'value'|\n" +
                                       "| CHOOSE              | Is a list of challenge options; the items are the keys (or values)|\n" +
                                       "| KEYFILE             | Is a text which needs to be signed; the text is found under the key 'value'|\n" +
                                       "| POLL                | The /login route has to be polled until the user confirms the login at the mobile device|\n" +
                                       "| OPTICAL_CHALLENGE   | Is a BASE64 encoded image (PNG format); the data is found under the key 'value'|\n" +
                                       "| OAUTH_AUTHORIZE     | Is an url that needs to be opened in a new tab; send empty challenge; the url is found under the key 'value'|\n" +
                                       "| OAUTH_POLL          | The /login route has to be polled until the user authorized Deeppay; send empty challenge; the url is found under the key 'value'|\n" +
                                       "| OAUTH_REFRESH_TOKEN | Send refresh token in challenge (or empty if unavailable)|\n" +
                                       "| CHANGE_PASSWORD     | The field 'passwordNew' must be sent to the server with the user's input.|\n";

  String OPERATION_PAYMENT_SUMMARY = "Method to upload a payment file in Pain001 format.";
  String OPERATION_PAYMENT_DESCRIPTION = "This route is used to upload payment files. The format of the file must be defined according to the pain001 specification. The file may contain one or more payments and can be uploaded as plain text. If a pain001 is upload the result might contain a pain002 file that is zipped and base64 encoded.";

  String OPERATION_LOGOUT_SUMMARY = "Method to perform a logout at the institute.";
  String OPERATION_LOGOUT_DESCRIPTION = "This route is used to logout the current session at the institute. The transport data can not be used anymore after logout was called.";

  String OPERATION_STATEMENT_SUMMARY = "Method to download an accounting file.";
  String OPERATION_STATEMENT_DESCRIPTION = "This route is used to download a file. The returned file content format depends on the format request parameter that is passed. The file is zipped and base64 encoded.";

  String OPERATION_AUTHORIZE_SUMMARY = "This endpoint is used to interact with the resource owner and get the authorization to access the protected resource.";
  String OPERATION_AUTHORIZE_DESCRIPTION = "This is the starting point of the oauth login flow. The result of this endpoint redirects you to a consent screen of the institute, where you will be asked to authorize the service to access some of your data (protected resources)";

  String OPERATION_TOKEN_SUMMARY = "This endpoint is used to get an access- or a refresh token";
  String OPERATION_TOKEN_DESCRIPTION = "The endpoint is called to get the tokens after the /authorize method was called. In this case the grandtype is 'authorizationcode'. To get a new access token after it was expired the grandtype in the request has to be set to 'refreshToken'.";

  String RESPONSE_AUTHORIZE_REDIRECT_DESCRIPTION = "Redirect url to a consent screen, where the user will be asked to authorize the service to get access to the data";

  String AUTHORIZE_REQUEST_BODY_DESCRIPTION = "Authorize request";

  String LOGIN_REQUEST_BODY_DESCRIPTION = "Login request";

  String SCHEMA_TRANSPORT_DATA_DESCRIPTION = "The transport data value contains session information and must be passed with the next request.";
  String SCHEMA_CHALLENGE_DESCRIPTION = "Might contain a challenge object (e.g an image of a photo tan) that is used for a second factor authentication. The challenge object is also used to change the login method. In this case a list of possible login method in return. The result of the challenge must be return in the challenge field";
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

  String SCHEMA_BANK_ID_DESCRIPTION = "The bankId is the bank identification received by the discovery route. It is used to forward the request to the right bank.";
  String SCHEMA_LANGUAGE_DESCRIPTION = "Language that might be used for result message.";
  String SCHEMA_REFRESH_TOKEN_DESCRIPTION = "The refresh token is used to get a new access token when the current token expires";
  String SCHEMA_CLIENT_ID_DESCRIPTION = "The Identifier of the application that asks for authorization.";

  String QUERY_BANK_ID_NAME = "bankId";
  String QUERY_BANK_ID_DESCRIPTION = SCHEMA_BANK_ID_DESCRIPTION;
  String QUERY_BANK_ID_EXAMPLE = "6300";

  String QUERY_CLIENT_ID_NAME = "ClientId";
  String QUERY_CLIENT_ID_DESCRIPTION = SCHEMA_CLIENT_ID_DESCRIPTION;

  String QUERY_STATE_NAME = "state";
  String QUERY_STATE_DESCRIPTION = "An opaque value, used for security purposes. Can be used to retrieve information on the clients application.";

  String QUERY_REDIRECT_URL_NAME = "redirectUrl";
  String QUERY_REDIRECT_URL_DESCRIPTION = "The URL to which the Server will send (through a redirect) the Authorization code, after the user login.";

}
