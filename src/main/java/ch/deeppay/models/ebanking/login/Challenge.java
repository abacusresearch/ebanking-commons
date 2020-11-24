package ch.deeppay.models.ebanking.login;

import ch.deeppay.util.DownloadFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * General information about a challenge from the 1st login step.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class Challenge {

  private String id;
  private DownloadFormat format; // e.g. base64, text
  private Map<String, String> content;
}
