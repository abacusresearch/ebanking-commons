package ch.deeppay.models.statement;

import ch.deeppay.models.ClientResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatementResponse extends ClientResponse {

  private String format;
  private String file;
}
