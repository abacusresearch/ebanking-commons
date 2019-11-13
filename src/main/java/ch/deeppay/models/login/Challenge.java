package ch.deeppay.models.login;

import ch.deeppay.util.DownloadFormat;
import lombok.Data;

import java.util.Map;

/**
 * General information about a challenge from the 1st login step.
 */
@Data
public class Challenge {

  private String id;
  private DownloadFormat format; // e.g. base64, text
  private Map<String, String> content;
}
