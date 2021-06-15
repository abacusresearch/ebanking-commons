package ch.deeppay.util;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public enum FileFormat {

  MT940("MT940", ".sta", true, false),
  MT942("MT942", ".sta", true, false),
  CAMT052("CAMT052", ".c52.xml", true, false),
  CAMT053("CAMT053", ".c53.xml", true, false),
  CAMT054("CAMT054", ".c54.xml", true, false),
  CAMT054_CDTN_DBTN("CAMT054_CDTN_DBTN", ".cs2.xml", true, false),
  CAMT054_ESR_LSV("CAMT054_ESR_LSV", ".xml", true, false),
  CAMT054_ZA("CAMT054_ZA", ".xml", true, false),

  ESR("ESR", ".v11", true, false),
  LSV_CHL("LSV_CHL", ".lsv", false, true),
  LSV_CLG("LSV_CLG", ".lsv", true, false),
  EDOC("EDOC", ".pdf", true, false),

  DTA("DTA", ".dta", false, true),
  PAIN001("PAIN001", ".xml", false, true),
  PAIN002("PAIN002", ".z01.xml", true, false),
  PAIN008("PAIN008", ".xml", false, true);

  private final String name;
  private final String fileExtension;
  private final boolean upload;
  private final boolean download;

  FileFormat(@NonNull final String name, @Nonnull final String fileExtension, final boolean download, final boolean upload) {
    this.name = name;
    this.fileExtension = fileExtension;
    this.upload = upload;
    this.download = download;
  }

  @Nonnull
  public String getFileExtension() {
    return fileExtension;
  }

  public boolean isDownload() {
    return download;
  }

  public boolean isUpload() {
    return upload;
  }

  @Override
  public String toString() {
    return name;
  }

  @Nullable
  public static FileFormat validateUpload(@Nullable final String formatAsString) {
    return validate(formatAsString, FileFormat::isUpload);
  }

  @Nullable
  public static FileFormat validateDownload(@Nullable final String formatAsString) {
    return validate(formatAsString, FileFormat::isDownload);
  }

  @Nullable
  private static FileFormat validate(@Nullable final String formatAsString, @NonNull final Predicate<FileFormat> type) {
    if (formatAsString == null) {
      return null;
    }
    List<FileFormat> fileFormats = Arrays.stream(FileFormat.values()).filter(type).collect(Collectors.toList());

    try {
      FileFormat format = valueOf(formatAsString.toUpperCase()); // throws illegal argument exception
      if (!fileFormats.contains(format)) {
        throw new IllegalArgumentException();
      }
      return format;
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actual fileFormat: [" + formatAsString.toUpperCase() + "]. Required: " + fileFormats);
    }
  }
}
