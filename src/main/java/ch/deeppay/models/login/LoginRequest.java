package ch.deeppay.models.login;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

/**
 * Bean for each request on the login route.
 */
@Data
public class LoginRequest {

  private String contractId = StringUtils.EMPTY;
  private String password = StringUtils.EMPTY;
  private String challenge = StringUtils.EMPTY;
  private String passwordNew = StringUtils.EMPTY;
  private String bankId = StringUtils.EMPTY;
  private String language = StringUtils.EMPTY;
  private String transportData = StringUtils.EMPTY;
}
