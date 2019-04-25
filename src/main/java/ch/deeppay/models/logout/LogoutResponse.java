package ch.deeppay.models.logout;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

@Data
public class LogoutResponse {

  private int code = 0;
  private String message = StringUtils.EMPTY;
}