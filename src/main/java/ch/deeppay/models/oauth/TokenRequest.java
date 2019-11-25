package ch.deeppay.models.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TokenRequest {

  private String bankId;
  private String language;

  private String grantType;
  private String code;
  private String clientId;
  private String clientSecret;
  private String refreshToken;
  private String redirectUrl;

  // filled up by server
  private String instituteUrl;
  private String transportData;
  private String tokenUrl;
}
