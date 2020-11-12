package ch.deeppay.models.accounting;

import ch.deeppay.util.validators.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequest {

  @UUID
  private @NotNull String abacusId;
  private @NotNull String bankId;
  private String cardNumber;
}
