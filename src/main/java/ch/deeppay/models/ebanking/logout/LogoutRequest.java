package ch.deeppay.models.ebanking.logout;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class LogoutRequest {

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  private String transportData;
}
