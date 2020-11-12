package ch.deeppay.models.ebanking.statement.elements;

import ch.deeppay.models.ebanking.statement.types.FintechVariation;
import ch.deeppay.models.ebanking.statement.types.InsituteVariation;
import lombok.Data;

/**
 * Represents a fintech.
 */
@Data
public class Institute {

  private FintechVariation id;
  private InsituteVariation type;

}
