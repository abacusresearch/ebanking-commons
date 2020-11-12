package ch.deeppay.models.ebanking.login;

import ch.deeppay.util.DownloadFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * General information about a challenge from the 1st login step.
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Challenge {

  private String id;
  private DownloadFormat format; // e.g. base64, text
  private Map<String, String> content;
}
