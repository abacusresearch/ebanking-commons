package ch.deeppay.models.accounting.elements;

import ch.deeppay.models.accounting.types.InsituteVariation;
import ch.deeppay.models.accounting.types.FintechVariation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a fintech.
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Institute {

  private FintechVariation id;
  private InsituteVariation type;

}
