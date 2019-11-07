package ch.deeppay.models.statement;

import ch.deeppay.models.ClientResponse;
import ch.deeppay.util.FileFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatementResponse extends ClientResponse {

  private FileFormat format;
  private String file;
}
