package ch.deeppay.models.transportdata;

import javax.annotation.Nonnull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

public class TransportDataUtil {

  public static final String DEFAULT_LANGUAGE = "en";

  public static final String SEPARATOR = "!";
  public static final String PATTERN = "=([^!]+)";

  public static final String BANK_ID = "b";
  public static final Pattern BANK_ID_PATTERN = Pattern.compile(BANK_ID + PATTERN);

  public static final String LANGUAGE = "l";
  public static final Pattern LANGUAGE_PATTERN = Pattern.compile(LANGUAGE + PATTERN);

  public static final String SESSION = "s";
  public static final Pattern SESSION_PATTERN = Pattern.compile(SESSION + PATTERN);

  @Nonnull
  public static String getElement(@Nonnull Pattern pattern, @Nullable String transportData) {
    if (StringUtils.isNotEmpty(transportData)) {
      Matcher matcher = pattern.matcher(transportData);
      if (matcher.find()) {
        return matcher.group(1);
      }
    }
    return StringUtils.EMPTY;
  }

  @Nonnull
  public static String getKeyValuePair(@Nonnull String key, @Nullable String value, boolean first) {
    if (StringUtils.isEmpty(value)) {
      return StringUtils.EMPTY;
    }

    return (first ? StringUtils.EMPTY : SEPARATOR) + key + '=' + value;
  }

  @Nonnull
  public static String getKeyValuePair(@Nonnull String key, @Nullable String value) {
    return getKeyValuePair(key, value, false);
  }
}

