package ch.deeppay.models.ebanking.oauth;

import ch.deeppay.spring.openapi.ebanking.OpenApiTokenRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class TokenRequest implements OpenApiTokenRequest {

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
