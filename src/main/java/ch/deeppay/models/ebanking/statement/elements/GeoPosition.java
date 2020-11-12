package ch.deeppay.models.ebanking.statement.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Detail position
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoPosition {

  private BigDecimal lat;
  private BigDecimal lon;
}
