package ch.deeppay.models.ebanking.statement;

import java.time.Instant;

import ch.deeppay.spring.constraints.FileFormatConstraint;
import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value = JsonInclude.Include.NON_EMPTY)
public class StatementRequest {

  private String transportData;

  @NonNull
  @FileFormatConstraint
  @Builder.Default
  private String format = "";
  @Nullable private String account;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Instant dateFrom;

  @Nullable
  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
  private Instant dateTo;

  @Nullable private String dataType;
  @Nullable private String transactionType;
  @Nullable private Boolean details;
  @Nullable private Boolean withOld;

  @SuppressWarnings("unused")
  public FileFormat getFormat() {
    return FileFormat.valueOf(format.toUpperCase());
  }


  @Nullable
  public FileFormat getFormatNullable() {
    return FileFormat.fromStr(format);
  }
}

