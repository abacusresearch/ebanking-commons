package ch.deeppay.models.transportdata;

import javax.annotation.Nonnull;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.springframework.lang.Nullable;

import static java.lang.String.format;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class TransportDataUtil {


  public static final String DEFAULT_LANGUAGE = "en";

  public static final String SEPARATOR = "!";
  public static final String PATTERN = "(^|!)(%s)=([^!]+)";
  private static final int PATTERN_MATCHING_GROUP = 3;

  public static final String BANK_ID = "b";
  public static final Pattern BANK_ID_PATTERN = createPattern(BANK_ID);

  public static final String LANGUAGE = "l";
  public static final Pattern LANGUAGE_PATTERN = createPattern(LANGUAGE);

  public static final String SESSION = "s";
  public static final Pattern SESSION_PATTERN = createPattern(SESSION);

  @Nonnull
  public static String getElement(@Nonnull Pattern pattern, @Nullable String transportData) {
    if (isNotEmpty(transportData)) {
      Matcher matcher = pattern.matcher(transportData);
      if (matcher.find()) {
        return matcher.group(PATTERN_MATCHING_GROUP);
      }
    }
    return getElement(pattern, transportData, false);
  }

  @Nonnull
  public static String getElement(@Nonnull Pattern pattern, @Nullable String transportData, boolean isDecodeValue) {
    if (isNotEmpty(transportData)) {
      Matcher matcher = pattern.matcher(transportData);
      if (matcher.find()) {
        String element = matcher.group(PATTERN_MATCHING_GROUP);
        return isDecodeValue ? decode(element) : element;
      }
    }
    return StringUtils.EMPTY;
  }

  @Nonnull
  public static String getKeyValuePair(@Nonnull String key, @Nullable String value, boolean first, boolean isEncodeValue) {
    if (StringUtils.isEmpty(value)) {
      return StringUtils.EMPTY;
    }

    return (first ? StringUtils.EMPTY : SEPARATOR) + key + '=' + (isEncodeValue ? encode(value) : value);
  }

  private static String encode(String value) {
    try {
      return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

  private static String decode(String value) {
    try {
      return URLDecoder.decode(value, StandardCharsets.UTF_8.toString());
    } catch (UnsupportedEncodingException e) {
      throw new IllegalStateException(e);
    }
  }

  @Nonnull
  public static String getKeyValuePair(@Nonnull String key, @Nullable String value) {
    return getKeyValuePair(key, value, false, false);
  }


  @Nonnull
  public static Pattern createPattern(@Nonnull String id) {
    return Pattern.compile(format(PATTERN, id));
  }


}

