package ch.deeppay.util;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;

import java.net.HttpCookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
   * @param setCookieHeaders {@link HttpHeaders} to handle
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
   * Transforms a given cookie map into a MultiValueMap.
   *
   * @param cookies map to handle
   * @return string array
   */
  public static String[] getCookiesAsArray(@NonNull Map<String, String> cookies) {
    List<String> result = new ArrayList<>();
    cookies.forEach((name, value) -> result.add(name + '=' + value));
    return result.toArray(EMPTY_ARRAY);
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

  public static HttpHeaders extractSetCookies(@NonNull HttpHeaders setCookieHeaders) {
    return getCookiesAsSetCookieHeader(getSetCookies(setCookieHeaders));
  }

}
