package ch.deeppay.models;

import ch.deeppay.util.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse {

  private StatusCode code;
  private String message;
  private String transportData = StringUtils.EMPTY;

  // should not displayed to user
  private List<String> errors = null;
}
