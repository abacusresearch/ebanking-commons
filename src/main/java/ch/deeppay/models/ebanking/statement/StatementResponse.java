package ch.deeppay.models.ebanking.statement;

import ch.deeppay.models.ebanking.ClientResponse;
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
