package ch.deeppay.models.ebanking.oauth;

import javax.validation.constraints.NotBlank;

import ch.deeppay.spring.openapi.ebanking.OpenApiAuthorizeRequest;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizeRequest implements OpenApiAuthorizeRequest {

  private @NotBlank(message = "bankId is mandatory") String bankId;
  private @NotBlank(message = "clientId is mandatory") String clientId;
  private @NotBlank(message = "state is mandatory") String state;
  private @NotBlank(message = "redirectUrl is mandatory") String redirectUrl;
}
