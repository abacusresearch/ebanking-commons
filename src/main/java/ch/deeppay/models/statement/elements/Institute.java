package ch.deeppay.models.statement.elements;

import ch.deeppay.models.statement.types.FintechVariation;
import ch.deeppay.models.statement.types.InsituteVariation;
import lombok.Data;

/**
 * Represents a fintech.
 */
@Data
public class Institute {

  private FintechVariation id;
  private InsituteVariation type;

}
