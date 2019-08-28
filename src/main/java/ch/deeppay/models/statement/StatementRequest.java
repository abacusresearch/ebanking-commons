package ch.deeppay.models.statement;

import ch.deeppay.util.FileFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementRequest {

  private @NotBlank(message = "TransportData cannot be blank.") String transportData;
  @Nullable private FileFormat format;
  @Nullable private String account;
  @Nullable private Date dateFrom;
  @Nullable private Date dateTo;
  @Nullable private String dataType;
  @Nullable private String transactionType;
  @Nullable private Boolean details;

  public void setFormat(final String format) {
    this.format = FileFormat.validateDownload(format);
  }

}

