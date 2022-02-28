package ch.deeppay.models.accounting;

import java.util.Date;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * Nimmt die Parameter eines Statementrequests vom Client zu Revolut entgegen. Das Transportdata wird vom Client nur
 * weitergeleitet, er bekommt dies bereits aus dem Loginresponse von uns
 */
@Data
public class StatementRequest {

  private String creditCardNumber;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date from;
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private Date to;
  private TransportData transportData;
  private String updateCardNumber;
  private String updateCardBrand;
  private Integer limit;
  private String mail;
  private String internalAccountId;
}
