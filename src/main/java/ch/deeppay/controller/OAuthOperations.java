package ch.deeppay.controller;

import ch.deeppay.models.oauth.AuthorizeRequest;
import ch.deeppay.models.oauth.TokenRequest;
import ch.deeppay.models.oauth.TokenResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/oauth", produces = MediaType.APPLICATION_JSON_VALUE)
public interface OAuthOperations {

  /**
   * @param clientId to identify the calling application e. g. abaninja.
   * @param redirectUrl the URL to which the Server will send (through a redirect) the Authorization code, after the user login.
   * @param state parameter will be returned. Can be used to retrieve information on the clients application.
   */
  @GetMapping(path = "/authorize")
  ModelAndView handleAuthorize(@NonNull final AuthorizeRequest oAuthAuthorizeRequest);

  /**
   * @param grandType The grant type of the request, must be refresh_token when refreshing an access token.
   * Must be authorization_code for the initial request to get the access and refresh tokens.
   * @param code The code parameter returned to your redirect URI when the user authorized your app.
   * @param clientId The Client ID of your app.
   * @param clientSecret The Client Secret of your app.
   * @param refreshToken The refresh token obtained when initially authenticating your OAuth integration.
   * @param redirectUrl The redirect URI that was used when the user authorized your app. This must exactly match the redirect_uri used when intiating the OAuth 2.0 connection.
   * @return TokenResponse
   */
  @PostMapping(path = "/token", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
  ResponseEntity<TokenResponse> handleToken(@NonNull TokenRequest tokenRequest);

}
