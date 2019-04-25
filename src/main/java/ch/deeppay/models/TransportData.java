package ch.deeppay.models;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Data that must be sent with the next request.
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TransportData {

  private static final String SEPARATOR = "!";
  private static final String BANK_ID = "b";
  private static final String LANGUAGE_ID = "l";
  private static final String URL_ID = "u";
  private static final Pattern BANK_PATTERN = Pattern.compile(BANK_ID + "=([^!]+)");
  private static final Pattern LANGUAGE_PATTERN = Pattern.compile(LANGUAGE_ID + "=([^!]+)");
  private static final Pattern URL_PATTERN = Pattern.compile(URL_ID + "=([^!]+)");

  private String bankId = StringUtils.EMPTY;
  private String language = Locale.GERMAN.getLanguage();
  private String interfaceUrl = null;

  @NonNull
  public static String convertIntoTransportString(@NonNull final TransportData transportData) {
    return getKeyValuePair(BANK_ID, transportData.getBankId(), false)
           + getKeyValuePair(LANGUAGE_ID, transportData.getLanguage(), false)
           + getKeyValuePair(URL_ID, transportData.getInterfaceUrl(), true);
  }

  @NonNull
  public static TransportData getTransportData(@NonNull final String transportDataAsString) {
    TransportData transportData = new TransportData();
    transportData.setBankId(getBankId(transportDataAsString));
    transportData.setLanguage(getLanguage(transportDataAsString));
    transportData.setInterfaceUrl(decode(getUrl(transportDataAsString)));
    return transportData;
  }

  private static String getUrl(@Nullable final String transportDataAsString) {
    return getElement(URL_PATTERN, transportDataAsString);
  }

  private static String getLanguage(@Nullable final String transportDataAsString) {
    return getElement(LANGUAGE_PATTERN, transportDataAsString);
  }

  @NonNull
  private static String getKeyValuePair(@NonNull final String key, @Nullable final String value, final boolean urlEnconding) {
    if (value == null || value.isEmpty()) {
      return StringUtils.EMPTY;
    }
    StringBuilder transportElement = new StringBuilder();
    if (!key.equals(BANK_ID)) {
      transportElement.append(SEPARATOR);
    }

    transportElement.append(key);
    transportElement.append("=");

    if (urlEnconding) {
      transportElement.append(encode(value));
    } else {
      transportElement.append(value);
    }
    return transportElement.toString();
  }

  @NonNull
  public static String getBankId(@Nullable final String transportData) {
    return getElement(BANK_PATTERN, transportData);
  }

  private static String getElement(@NonNull final Pattern pattern, @Nullable final String transportData) {
    if (transportData != null && !transportData.isEmpty()) {
      Matcher matcher = pattern.matcher(transportData);
      if (matcher.find()) {
        return matcher.group(1);
      }
    }
    return StringUtils.EMPTY;
  }

  @NonNull
  private static String encode(@NonNull final String url) {
    try {
      return URLEncoder.encode(url, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      // not going to happen - value came from JDKs own StandardCharsets
      return StringUtils.EMPTY;
    }
  }

  @NonNull
  private static String decode(@NonNull final String url) {
    try {
      return URLDecoder.decode(url, StandardCharsets.UTF_8.name());
    } catch (UnsupportedEncodingException e) {
      // not going to happen - value came from JDKs own StandardCharsets
      return StringUtils.EMPTY;
    }
  }
}
