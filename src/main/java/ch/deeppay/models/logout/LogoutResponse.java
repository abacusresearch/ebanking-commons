package ch.deeppay.models.logout;

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

  private int code = 0;
  private String message = StringUtils.EMPTY;
}