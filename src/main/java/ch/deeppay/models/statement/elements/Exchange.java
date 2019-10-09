package ch.deeppay.models.statement.elements;

import ch.deeppay.util.serializer.RateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

@Data
public class Exchange {

  private Amount fromAmount;
  private Amount toAmount;
  @JsonSerialize(using = RateSerializer.class)
  private Double rate;
}
