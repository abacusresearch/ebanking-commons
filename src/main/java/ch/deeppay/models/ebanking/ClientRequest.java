package ch.deeppay.models.ebanking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class ClientRequest {

  private @NotNull String transportData;
}