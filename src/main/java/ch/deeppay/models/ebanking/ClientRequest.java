package ch.deeppay.models.ebanking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @deprecated use specific request beans (e.g. loginRequest, logoutRequest, ...)
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
@Deprecated
public class ClientRequest {

  private @NotNull String transportData;
}
