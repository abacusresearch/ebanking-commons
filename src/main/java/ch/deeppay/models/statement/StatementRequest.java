package ch.deeppay.models.statement;

import ch.deeppay.util.FileFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementRequest {

  @Nullable private String statementId;
  @Nullable private FileFormat format;
  @Nullable private Date dateFrom;
  @Nullable private Date dateTo;
  private String transportData;

  public void setFormat(final String format) {
    this.format = FileFormat.validateDownload(format);
  }

}

