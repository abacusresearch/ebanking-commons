package ch.deeppay.models.statement;

import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class StatementRequest {

  private @NotBlank(message = "TransportData cannot be blank.") String transportData;

  @NonNull
  @FileFormatConstraint
  private String format = "";
  @Nullable private String account;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date dateFrom;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Date dateTo;

  @Nullable private String dataType;
  @Nullable private String transactionType;
  @Nullable private Boolean details;

  @SuppressWarnings("unused")
  public FileFormat getFormat() {
    return FileFormat.valueOf(format.toUpperCase());
  }
}

