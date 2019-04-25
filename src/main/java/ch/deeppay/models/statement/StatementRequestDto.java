package ch.deeppay.models.statement;

import ch.deeppay.models.TransportData;
import ch.deeppay.util.CookieHelper;
import ch.deeppay.util.FileFormat;
import ch.deeppay.util.ParameterException;
import lombok.Data;
import lombok.NonNull;
import org.springframework.http.HttpHeaders;

import java.util.Date;
import java.util.List;

@Data
public class StatementRequestDto {

  private String[] cookies;
  private String statementId;
  private FileFormat format;
  private List<String> accounts;
  private Date dateFrom;
  private Date dateTo;
  private TransportData transportData;

  public void setFormat(final @NonNull String format) {
    this.format = FileFormat.validateDownload(format);
  }

  public void setCookies(final @NonNull HttpHeaders headers) {
    String[] cookies = CookieHelper.getClientCookies(headers);
    if (cookies.length == 0) {
      throw new ParameterException("Missing Parameter: [Cookie]");
    }
    this.cookies = cookies;
  }
}
