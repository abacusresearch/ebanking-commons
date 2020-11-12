package ch.deeppay.models.accounting;

import lombok.Data;
import org.apache.commons.lang.StringUtils;

import javax.validation.constraints.NotNull;

/**
 * Nimmt die Parameter eines Loginrequests vom Client zu Revolut OAuth2 entgegen.
 */
@Data
public class LoginRequest {

  private @NotNull String bankId;
  private String accessToken = StringUtils.EMPTY;
  private String refreshToken = StringUtils.EMPTY;
  private String clientId = StringUtils.EMPTY;
  private String clientSecret = StringUtils.EMPTY;
}
