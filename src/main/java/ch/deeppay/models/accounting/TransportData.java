package ch.deeppay.models.accounting;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class TransportData {

  private String accessToken = StringUtils.EMPTY;
  private String bankId = StringUtils.EMPTY;
  private String refreshToken = StringUtils.EMPTY;
  private String clientId = StringUtils.EMPTY;
  private String clientSecret = StringUtils.EMPTY;
  private String timestamp = StringUtils.EMPTY;

}
