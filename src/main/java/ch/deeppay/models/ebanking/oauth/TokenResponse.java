package ch.deeppay.models.ebanking.oauth;

import ch.deeppay.spring.openapi.ebanking.OpenApiTokenResponse;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class TokenResponse implements OpenApiTokenResponse {

  private String accessToken;
  private String tokenType;
  private String refreshToken;
  private String transportData;
  private String bankId;
}
