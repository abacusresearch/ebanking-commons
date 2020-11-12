package ch.deeppay.models.accounting;

import ch.deeppay.models.accounting.statement.Statement;
import ch.deeppay.models.ebanking.ClientResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StatementResponse extends ClientResponse {

  private List<Statement> statements;
}
