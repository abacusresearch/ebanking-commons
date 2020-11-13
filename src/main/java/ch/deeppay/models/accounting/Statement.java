package ch.deeppay.models.accounting;

import ch.deeppay.models.accounting.elements.Exchange;
import ch.deeppay.models.accounting.elements.Institute;
import ch.deeppay.models.accounting.elements.Merchant;
import ch.deeppay.models.accounting.elements.Payout;
import ch.deeppay.models.accounting.types.Amount;
import ch.deeppay.models.accounting.types.BookingType;
import ch.deeppay.models.accounting.types.CreditCard;
import ch.deeppay.models.accounting.types.PaymentState;
import ch.deeppay.models.accounting.types.StatementType;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Statement {

  private String id;
  private Institute institute;

  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "GMT")
  private Date date;

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
  private Payout payout;
}
