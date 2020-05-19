package ch.deeppay.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeepPayProblem {

  private String title;
  private String detail;
  private int status;
  private String instance;
  private String id;
}
