package ch.deeppay.models.statement;

import ch.deeppay.util.FileFormat;
import lombok.Data;

import java.util.Date;

@Data
public class StatementRequest {

  private String statementId;
  private FileFormat format;
  private Date dateFrom;
  private Date dateTo;
  private String transportData;

  public void setFormat(final String format) {
    this.format = FileFormat.validateDownload(format);
  }

}

