package ch.deeppay.models.ebanking.logout;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang.StringUtils;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LogoutResponse {

  private int code;
  @Builder.Default
  private String message = StringUtils.EMPTY;
}
