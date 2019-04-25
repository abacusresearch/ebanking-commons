package ch.deeppay.util;

import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CookieHelper {

  /**
   * Extracts all cookies (no set-cookies) from client header.
   */
  @NonNull
  public static String[] getClientCookies(@NonNull final HttpHeaders clientHeader) {
    List<String> cookies = clientHeader.get(HttpHeaders.COOKIE);
    if (cookies == null) {
      return new String[]{};
    }

    Map<String, String> keyValueCookies = getCookies(cookies);
    List<String> cookieHolder = new ArrayList<>();

    keyValueCookies.forEach((key, value) -> cookieHolder.add(key.trim() + "=" + value.trim()));
    String[] array = new String[cookieHolder.size()];
    return cookieHolder.toArray(array);
  }

  /**
   * Create set cookies from client request.
   */
  @NonNull
  public static HttpHeaders createSetCookies(@Nullable final HttpHeaders from) {
    HttpHeaders newHeader = new HttpHeaders();
    if (from != null) {
      newHeader.addAll(putToSetCookie(from, HttpHeaders.COOKIE));
      newHeader.addAll(putToSetCookie(from, HttpHeaders.SET_COOKIE));
    }
    return newHeader;
  }

  /**
   * Creates set cookies. If the server does not return any cookies, the cookies sent by the client are returned.
   *
   * @param serverResponse headers from bank server
   * @param clientRequest headers from client
   */
  public static HttpHeaders createClientCookies(@NonNull final HttpHeaders serverResponse, @Nullable final HttpHeaders clientRequest) {
    Map<String, String> cookies = new HashMap<>();
    cookies.putAll(getClientSetCookies(serverResponse));
    cookies.putAll(getClientSetCookies(clientRequest));

    HttpHeaders cleanCookes = new HttpHeaders();
    cookies.forEach((key, value) -> cleanCookes.add(HttpHeaders.SET_COOKIE, key + "=" + value));

    return cleanCookes;
  }

  @NonNull
  private static Map<String, String> getClientSetCookies(@Nullable final HttpHeaders headers) {
    HttpHeaders clientHeaders = createSetCookies(headers);
    return getCookies(clientHeaders.get(HttpHeaders.SET_COOKIE));
  }

  @NonNull
  public static Map<String, String> getCookies(@Nullable final List<String> oldCookies) {
    if (oldCookies != null) {
      List<String> preparedCookieString = oldCookies.stream()
          .filter(Objects::nonNull)
          .filter(e -> !e.isEmpty())
          .collect(Collectors.toList());

      return preparedCookieString.stream()
          .flatMap(Pattern.compile(";")::splitAsStream)
          .flatMap(Pattern.compile(",")::splitAsStream)
          .filter(e -> !e.isEmpty())
          .filter(e -> e.contains("="))
          .filter(e -> !e.contains("/"))
          .map(str -> str.split("="))
          .filter(str -> !str[0].isEmpty())
          .collect(Collectors.toMap(str -> str[0], str -> str[1]));
    }
    return new HashMap<>();
  }

  private static HttpHeaders putToSetCookie(@NonNull final HttpHeaders from, @NonNull final String type) {
    HttpHeaders newHeader = new HttpHeaders();
    List<String> cookies = from.get(type);
    if (cookies != null) {
      for (String cookie : cookies) {
        newHeader.add(HttpHeaders.SET_COOKIE, cookie);
      }
    }
    return newHeader;
  }
}
