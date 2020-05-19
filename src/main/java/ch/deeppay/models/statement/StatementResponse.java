package ch.deeppay.models.statement;

import ch.deeppay.models.ClientResponse;
import ch.deeppay.util.FileFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = true)
@Data
public class StatementResponse extends ClientResponse {

  private FileFormat format;
  private String file;
}
