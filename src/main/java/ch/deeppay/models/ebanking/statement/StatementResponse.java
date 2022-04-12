package ch.deeppay.models.ebanking.statement;

import java.util.List;

import ch.deeppay.models.ebanking.ClientResponse;
import ch.deeppay.util.FileFormat;
import ch.deeppay.util.serializer.FileFormatSerializer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class StatementResponse extends ClientResponse {

  @JsonSerialize(using = FileFormatSerializer.class)
  private FileFormat format;
  private String file;
  private String jobId;

  @Builder
  public StatementResponse(String message, String transportData, List<String> errors, FileFormat format, String file, String jobId) {
    super(message, transportData, errors);
    this.format = format;
    this.file = file;
    this.jobId = jobId;
  }

}
