package ch.deeppay.util;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public enum FileFormat {

  MT940("MT940", true, false),
  MT942("MT942", true, false),
  CAMT052("CAMT052", true, false),
  CAMT053("CAMT053", true, false),
  CAMT054("CAMT054", true, false),
  CAMT054_CDTN_DBTN("CAMT054_CDTN_DBTN", true, false),
  CAMT054_ESR_LSV("CAMT054_ESR_LSV", true, false),
  CAMT054_ZA("CAMT054_ZA", true, false),

  ESR("ESR", true, false),
  LSV("LSV", true, true),
  EDOC("EDOC", true, false),
  MT571("MT571", true, false),

  DTA("DTA", false, true),
  PAIN001("PAIN001", false, true),
  PAIN002("PAIN002", true, false),
  PAIN008("PAIN008", false, true);

  private final String name;
  private final boolean upload;
  private final boolean download;

  FileFormat(@NonNull final String name, final boolean download, final boolean upload) {
    this.name = name;
    this.upload = upload;
    this.download = download;
  }

  public boolean isDownload() {
    return download;
  }

  public boolean isUpload() {
    return upload;
  }

  @Override
  public String toString() {
    return this.name;
  }

  @Nullable
  public static FileFormat validateUpload(@Nullable final String formatAsString) {
    return validate(formatAsString, FileFormat::isUpload);
  }

  @Nullable
  public static FileFormat validateDownload(@Nullable final String formatAsString) {
    return validate(formatAsString, FileFormat::isDownload);
  }

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
      throw new ParameterException("Actual file format: [" + formatAsString.toUpperCase() + "]. Required: " + fileFormats);
    }
  }
}
