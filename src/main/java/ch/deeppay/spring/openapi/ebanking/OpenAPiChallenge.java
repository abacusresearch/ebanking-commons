package ch.deeppay.spring.openapi.ebanking;

import java.util.Map;

import ch.deeppay.util.DownloadFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "Challenge_", description = "Challenge object that contains different types of challenges")
public interface OpenAPiChallenge {

  @Schema(description = "Id of the challenge", example = "MOBILE_TAN", required = true)
  String getId();

  @Schema(description = "Type of the download format", required = true)
  DownloadFormat getFormat();

  @Schema(description = "If download format is BASE64 or TEXT, the content object contains a key with the name 'value' that contains the value of the challenge. If the download format is LIST the content object contains several key values pairs",
          example = "\"value\": \"1236\"", required = true)
  Map<String, String> getContent();


}
