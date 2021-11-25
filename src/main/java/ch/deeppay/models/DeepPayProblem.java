package ch.deeppay.models;

import java.net.URI;

import ch.deeppay.spring.openapi.OpenApiDeepPayProblem;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class DeepPayProblem implements OpenApiDeepPayProblem {

  private URI type;
  private String title;
  private String detail;
  private int status;
  private String instance;
  private String traceId;
  private String sessionId;
  @Deprecated
  private String id;
}
