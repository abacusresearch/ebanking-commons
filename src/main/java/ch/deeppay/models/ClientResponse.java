package ch.deeppay.models;

import ch.deeppay.util.StatusCode;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClientResponse {

  private int code = StatusCode.OK.getCode();
  private String message = StringUtils.EMPTY;
  private String transportData = StringUtils.EMPTY;

  // should not displayed to user
  private List<String> errors = null;
}
