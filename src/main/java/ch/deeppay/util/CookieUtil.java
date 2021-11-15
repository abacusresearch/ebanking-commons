package ch.deeppay.util;

import javax.annotation.Nonnull;
import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

import com.google.common.base.Splitter;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.apache.commons.lang.StringUtils.indexOf;
import static org.apache.commons.lang.StringUtils.substring;
import static org.apache.commons.lang.StringUtils.trim;

/**
 * Utility class for cookie extraction.
 */
public class CookieUtil {

  public static final String[] EMPTY_ARRAY = ArrayUtils.EMPTY_STRING_ARRAY;

  /**
   * Returns cookies included in given {@link HttpHeaders} with <b>Set-Cookie</b> headers. This is used to extract cookies sent by the bank server.
   *
   * @param setCookieHeaders {@link HttpHeaders} to handle
   * @return Map of cookies
   */
  public static Map<String, String> getSetCookies(@NonNull HttpHeaders setCookieHeaders) {
    Map<String, String> result = new HashMap<>();
    result.putAll(getCookies(setCookieHeaders, HttpHeaders.SET_COOKIE));
    result.putAll(getCookies(setCookieHeaders, HttpHeaders.SET_COOKIE2));
    return result;
  }

  /**
   * Extracts cookies.
   *
   * @param setCookieHeaders    {@link HttpHeaders} to handle
   * @param setCookieHeaderName fields to look for
   * @return Map of cookies
   */
  private static Map<String, String> getCookies(@NonNull HttpHeaders setCookieHeaders, @NonNull String setCookieHeaderName) {
    Map<String, String> result = new HashMap<>();
    List<String> headers = setCookieHeaders.get(setCookieHeaderName);
    if (headers != null) {
      headers.forEach(string -> {
        List<HttpCookie> cookies = HttpCookie.parse(string);
        cookies.forEach(httpCookie -> result.put(httpCookie.getName(), httpCookie.getValue()));

      });
    }
    return result;
  }

  /**
   * Returns a given cookie map as {@link HttpHeaders} with <b>Set-Cookie</b> headers.
   *
   * @param cookies map to handle
   * @return HttpHeaders
   */
  public static HttpHeaders getCookiesAsSetCookieHeader(@NonNull Map<String, String> cookies) {
    HttpHeaders result = new HttpHeaders();
    cookies.forEach((name, value) -> result.add(HttpHeaders.SET_COOKIE, name + '=' + value));
    return result;
  }

  public static HttpHeaders extractSetCookies(@NonNull HttpHeaders httpHeaders) {
    return getCookiesAsSetCookieHeader(getSetCookies(httpHeaders));
  }


  /**
   * Merge cookies from {@code HttpHeaders} and a string into a string again. Cookies in the string are comma separated.
   * The cookies from oldCookieList are overwritten when the same cookie exists in the HttpHeaders.
   *
   * @param headers
   * @param oldCookieList
   * @return
   */
  public static String mergeCookies(final HttpHeaders headers, final String oldCookieList) {
    Map<String, String> result = new TreeMap<>();

    //add old cookies before the new ones
    if (StringUtils.isNotBlank(oldCookieList)) {
      String[] splittedCookies = StringUtils.split(oldCookieList,",");
      if(ArrayUtils.isNotEmpty(splittedCookies)){
        //cookie value might contain '='. first occurance of '=' must be key value separator
        for(String str : splittedCookies){
          int first = indexOf(str,"=");
          result.put(trim(substring(str, 0, first)), trim(substring(str, first + 1)));
        }
      }
    }
    return getCookieValuesAsString(result, headers);
  }

  /**
   * Creates a list of Cookies.
   *
   * @param cookies map of cookies. key is cooke name and value is the cookie value.
   * @return list of cookies
   */
  @Nonnull
  public static List<String> getCookiesAsList(@Nonnull final Map<String, String> cookies) {
    List<String> result = new ArrayList<>();
    cookies.forEach((name, value) -> result.add(name + '=' + value));
    return result;
  }

  /**
   * Transforms a given cookie map into a MultiValueMap.
   *
   * @param cookies map to handle
   * @return string array
   */
  public static MultiValueMap<String, String> getCookiesAsMultiValueMap(@NonNull Map<String, String> cookies) {
    MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
    cookies.forEach(map::add);
    return map;
  }

  /**
   * Transforms a given cookie map into a MultiValueMap.
   *
   * @param cookies map to handle
   * @return string array
   */
  public static String[] getCookiesAsArray(@Nonnull final Map<String, String> cookies) {
    return getCookiesAsList(cookies).toArray(EMPTY_ARRAY);
  }

  public static String getCookiesAsString(@NonNull Map<String, String> cookies){
    return String.join(",", getCookiesAsArray(cookies));
  }

  private static String getCookieValuesAsString(@Nonnull final Map<String, String> result, final HttpHeaders headers) {
    if (Objects.nonNull(headers)) {
      //keep order -> set cookies are stronger than cookies
      result.putAll(getCookies(headers, HttpHeaders.COOKIE));
      result.putAll(getCookies(headers, HttpHeaders.SET_COOKIE));
      result.putAll(getCookies(headers, HttpHeaders.SET_COOKIE2));
    }
    return String.join(",", getCookiesAsList(result));
  }

  public static String getCookieAsString(HttpHeaders headers) {
    return getCookieValuesAsString(new HashMap<>(), headers);
  }

  /**
   * Return an array of cookies. The cookies in the string must be comma separated.
   *
   * @param cookieStr String of cookies separated by a comma
   * @return array of cookies
   */
  public static String[] getCookiesFromStr(final String cookieStr) {
    if (StringUtils.isEmpty(cookieStr)) {
      return ArrayUtils.EMPTY_STRING_ARRAY;
    }
    return StringUtils.split(cookieStr, ',');
  }

  public static Map<String, String> getCookiesFromStrAsMap(final String cookieStr){
    Map<String, String> result = new TreeMap<String, String>();
    if (StringUtils.isNotBlank(cookieStr)) {
      result.putAll(Splitter.on(",").withKeyValueSeparator("=").split(cookieStr));
    }
    return result;
  }
}
