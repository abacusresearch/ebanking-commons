package ch.deeppay.models.statement.elements;

import lombok.Data;

@Data
public class Merchant {

  private String id;
  private String category;
  private String categoryCode;
  private String firstLine;
  private String city;
  private String country;
  private String name;
  private String postCode;
  private String state;
  private GeoPosition location;
}
