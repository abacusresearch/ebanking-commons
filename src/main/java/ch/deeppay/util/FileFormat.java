package ch.deeppay.util;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

public class FileFormat implements FileFormatGetter {

  public static final FileFormat MT940 = new FileFormat("MT940", ".sta", true, false);
  public static final FileFormat MT942 = new FileFormat("MT942", ".sta", true, false);
  public static final FileFormat CAMT052 = new FileFormat("CAMT052", ".c52.xml", true, false);
  public static final FileFormat CAMT053 = new FileFormat("CAMT053", ".c53.xml", true, false);
  public static final FileFormat CAMT054 = new FileFormat("CAMT054", ".c54.xml", true, false);
  public static final FileFormat CAMT054_CDTN_DBTN = new FileFormat("CAMT054_CDTN_DBTN", ".cs2.xml", true, false);
  public static final FileFormat CAMT054_ESR_LSV = new FileFormat("CAMT054_ESR_LSV", ".xml", true, false);
  public static final FileFormat CAMT054_ZA = new FileFormat("CAMT054_ZA", ".xml", true, false);

  public static final FileFormat ESR = new FileFormat("ESR", ".v11", true, false);
  public static final FileFormat LSV_CHL = new FileFormat("LSV_CHL", ".lsv", false, true);
  public static final FileFormat LSV_CLG = new FileFormat("LSV_CLG", ".lsv", true, false);
  public static final FileFormat EDOC = new FileFormat("EDOC", ".pdf", true, false);

  public static final FileFormat DTA = new FileFormat("DTA", ".dta", false, true);
  public static final FileFormat PAIN001 = new FileFormat("PAIN001", ".xml", false, true);
  public static final FileFormat PAIN002 = new FileFormat("PAIN002", ".z01.xml", true, false);
  public static final FileFormat PAIN008 = new FileFormat("PAIN008", ".xml", false, true);

  private final String name;
  private final String fileExtension;
  private final boolean upload;
  private final boolean download;

  @SuppressWarnings({"StaticCollection", "DoubleBraceInitialization"})
  private static final ImmutableMap<String, FileFormat> standardFileFormats = ImmutableMap.copyOf(new HashMap<String, FileFormat>() {{
    put(MT940.name(), MT940);
    put(MT942.name(), MT942);
    put(CAMT052.name(), CAMT052);
    put(CAMT053.name(), CAMT053);
    put(CAMT054.name(), CAMT054);
    put(CAMT054_CDTN_DBTN.name(), CAMT054_CDTN_DBTN);
    put(CAMT054_ESR_LSV.name(), CAMT054_ESR_LSV);
    put(CAMT054_ZA.name(), CAMT054_ZA);
    put(ESR.name(), ESR);
    put(LSV_CHL.name(), LSV_CHL);
    put(LSV_CLG.name(), LSV_CLG);
    put(EDOC.name(), EDOC);
    put(DTA.name(), DTA);
    put(PAIN001.name(), PAIN001);
    put(PAIN002.name(), PAIN002);
    put(PAIN008.name(), PAIN008);
  }});


  public FileFormat(@NonNull final String name, @Nonnull final String fileExtension, final boolean download, final boolean upload) {
    this.name = name;
    this.fileExtension = fileExtension;
    this.upload = upload;
    this.download = download;
  }

  @Override
  @NonNull
  public String name() {
    return name;
  }

  public static FileFormat valueOf(@NonNull String name) {
    if (!standardFileFormats.containsKey(name)) {
      throw new IllegalArgumentException("Invalid FileFormat " + name);
    }
    return standardFileFormats.get(name);
  }

  @Override
  @Nonnull
  public String getFileExtension() {
    return fileExtension;
  }

  @Override
  public boolean isDownload() {
    return download;
  }

  @Override
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
    final List<FileFormat> fileFormats = standardFileFormats.values()
                                                            .stream()
                                                            .filter(type)
                                                            .collect(Collectors.toList());

    try {
      FileFormat format = standardFileFormats.get(formatAsString.toUpperCase()); // throws illegal argument exception
      if (!fileFormats.contains(format)) {
        throw new IllegalArgumentException();
      }
      return format;
    } catch (IllegalArgumentException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Actual fileFormat: [" + formatAsString.toUpperCase() + "]. Required: " + fileFormats);
    }
  }

  @Nullable
  public static FileFormat fromStr(@Nullable final String formatAsString) {
    if (StringUtils.isBlank(formatAsString)) {
      return null;
    }
    return standardFileFormats.get(formatAsString.toUpperCase());
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    FileFormat that = (FileFormat) o;

    //noinspection ObjectInstantiationInEqualsHashCode
    return new EqualsBuilder().append(upload, that.upload)
                              .append(download, that.download)
                              .append(name, that.name)
                              .isEquals();
  }

  @Override
  public int hashCode() {
    //noinspection ObjectInstantiationInEqualsHashCode
    return new HashCodeBuilder(17, 37).append(name).append(upload).append(download).toHashCode();
  }
}
