package ch.deeppay.models.statement.elements;

import ch.deeppay.util.serializer.MoneySerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class Amount {

  @JsonSerialize(using = MoneySerializer.class)
  private BigDecimal value;
  private String currency;
}
