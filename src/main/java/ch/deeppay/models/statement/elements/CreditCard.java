package ch.deeppay.models.statement.elements;

import lombok.Data;

@Data
public class CreditCard {

  private String number;
  private String firstName;
  private String lastName;
  private String phone;
  private String type;
  private String brand;
}
