package ch.deeppay.models.accounting.statement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
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
  private String user;
}
