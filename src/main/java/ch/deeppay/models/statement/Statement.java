package ch.deeppay.models.statement;

import ch.deeppay.models.statement.elements.Amount;
import ch.deeppay.models.statement.elements.CreditCard;
import ch.deeppay.models.statement.elements.Exchange;
import ch.deeppay.models.statement.elements.Institute;
import ch.deeppay.models.statement.elements.Merchant;
import ch.deeppay.models.statement.types.BookingType;
import ch.deeppay.models.statement.types.PaymentState;
import ch.deeppay.models.statement.types.StatementType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.Instant;

@Data
public class Statement {

  private String id;
  private Institute institute;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
  private Instant date;

  private Amount amount;
  private Amount fees;
  private Amount tip;
  private StatementType statementType;
  private BookingType bookingType;
  private PaymentState state;
  private String description;
  private Merchant merchant;
  private CreditCard creditCard;
  private Exchange exchange;
  private String category;
  private String categoryCode;
  private String paymentReference;
}
