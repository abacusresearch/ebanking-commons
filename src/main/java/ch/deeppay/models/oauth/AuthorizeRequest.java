package ch.deeppay.models.oauth;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AuthorizeRequest {

  private @NotBlank(message = "bankId is mandatory") String bankId;
  private @NotBlank(message = "clientId is mandatory") String clientId;
  private @NotBlank(message = "state is mandatory") String state;
  private @NotBlank(message = "redirectUrl is mandatory") String redirectUrl;
}
