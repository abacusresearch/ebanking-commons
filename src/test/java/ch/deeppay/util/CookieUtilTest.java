package ch.deeppay.util;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

class CookieUtilTest {

  @Test
  void testMergeCookiesWithEmptyHeader() {
    HttpHeaders clientHeaders = new HttpHeaders();
    String result = CookieUtil.mergeCookies(clientHeaders, null);

    Assertions.assertEquals("", result);
    result = CookieUtil.mergeCookies(clientHeaders, "");
    Assertions.assertEquals("", result);
    result = CookieUtil.mergeCookies(clientHeaders, " ");
    Assertions.assertEquals("", result);
  }

  @Test
  void testMergeCookiesWithOldCookieList() {
    HttpHeaders clientHeaders = new HttpHeaders();
    String result = CookieUtil.mergeCookies(clientHeaders, "key=value");
    Assertions.assertEquals("key=value", result);
    result = CookieUtil.mergeCookies(clientHeaders, "key1=value1,key2=value2");
    Assertions.assertEquals("key1=value1,key2=value2", result);
  }

  @Test
  void testMergeCookiesWithHeader() {
    MultiValueMap<String, String> clientCookies = new LinkedMultiValueMap<>();
    clientCookies.add(HttpHeaders.SET_COOKIE, "MY_SET_COOKIE=6789; Path=/; Secure; HttpOnly");
    clientCookies.add(HttpHeaders.COOKIE, "MY_COOKIE=1234; Path=/; HttpOnly");
    clientCookies.add(HttpHeaders.SET_COOKIE2, "MY_SET_COOKIE2=11223344; path=/");
    HttpHeaders clientHeaders = new HttpHeaders(clientCookies);

    String result = CookieUtil.mergeCookies(clientHeaders, null);
    Assertions.assertEquals("MY_COOKIE=1234,MY_SET_COOKIE=6789,MY_SET_COOKIE2=11223344", result);
  }

  @Test
  void testMergeCookiesWithHeaderAndOldCookies() {
    MultiValueMap<String, String> clientCookies = new LinkedMultiValueMap<>();
    clientCookies.add(HttpHeaders.SET_COOKIE, "MY_SET_COOKIE=6789; Path=/; Secure; HttpOnly");
    clientCookies.add(HttpHeaders.SET_COOKIE2, "MY_SET_COOKIE2=11223344; path=/");
    clientCookies.add(HttpHeaders.COOKIE, "MY_COOKIE=1234; Path=/; HttpOnly");
    HttpHeaders clientHeaders = new HttpHeaders(clientCookies);

    String result = CookieUtil.mergeCookies(clientHeaders, "key1=value1,key2=value2");
    Assertions.assertEquals("MY_COOKIE=1234,MY_SET_COOKIE=6789,MY_SET_COOKIE2=11223344,key1=value1,key2=value2", result);
  }


  @Test
  void testMergeCookiesWithOverwriteOldCookies() {
    MultiValueMap<String, String> clientCookies = new LinkedMultiValueMap<>();
    clientCookies.add(HttpHeaders.SET_COOKIE, "key1=new_value_1; Path=/; Secure; HttpOnly");
    clientCookies.add(HttpHeaders.COOKIE, "key1=old_value_1; Path=/; HttpOnly");
    HttpHeaders clientHeaders = new HttpHeaders(clientCookies);

    String result = CookieUtil.mergeCookies(clientHeaders, "key1=value1,key2=value2 ");
    Assertions.assertEquals("key1=new_value_1,key2=value2", result);
  }

  @Test
  void testMergeCookiesWithEmptyHeader2() {
    MultiValueMap<String, String> clientCookies = new LinkedMultiValueMap<>();
    HttpHeaders clientHeaders = new HttpHeaders(clientCookies);

    String result = CookieUtil.mergeCookies(clientHeaders, " AL_SESS-S=AfrE5CMu4wW50jEaTrvJY6F7oJxieplSSz7QiEW!CHt8xnolz5cWZdvE0nxZdQXXMmPw, CLX_EB_LOGIN_INFO=$xc/fmcLQ3L4bWJHEJ4Ah3an=mjYkyAJGhEdfUqRO7OThNGgMLkruols39P4I4EnrDM6Ephjss2QV0eT917JEeX2JbqoOSiesr5jtSKnke!K35oa8OKz0Q7ClMHME3Bwy_LF98DybTSktyKwM7!umOZMPUF6jjr0=");
    Assertions.assertEquals("AL_SESS-S=AfrE5CMu4wW50jEaTrvJY6F7oJxieplSSz7QiEW!CHt8xnolz5cWZdvE0nxZdQXXMmPw,CLX_EB_LOGIN_INFO=$xc/fmcLQ3L4bWJHEJ4Ah3an=mjYkyAJGhEdfUqRO7OThNGgMLkruols39P4I4EnrDM6Ephjss2QV0eT917JEeX2JbqoOSiesr5jtSKnke!K35oa8OKz0Q7ClMHME3Bwy_LF98DybTSktyKwM7!umOZMPUF6jjr0=", result);
  }

  @Test
  void testGetCookiesFromStr() {
    String[] result = CookieUtil.getCookiesFromStr("key1=value1,key2=value2");
    Assertions.assertEquals("key1=value1", result[0]);
    Assertions.assertEquals("key2=value2", result[1]);
  }

  @Test
  void testGetCookiesFromStrWithEmptyValues() {
    String[] result = CookieUtil.getCookiesFromStr("");
    Assertions.assertEquals(0, result.length);
  }

  @Test
  void testGetCookiesAsList() {
    Map<String, String> cookies = new TreeMap<>();
    List<String> result = CookieUtil.getCookiesAsList(cookies);
    Assertions.assertEquals(0, result.size());
    cookies.put("KEY1", "VALUE1");
    cookies.put("KEY2", "VALUE2");
    result = CookieUtil.getCookiesAsList(cookies);
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("KEY1=VALUE1", result.get(0));
    Assertions.assertEquals("KEY2=VALUE2", result.get(1));
  }

  @Test
  void testGetCookiesAsArray() {
    Map<String, String> cookies = new TreeMap<>();
    String[] result = CookieUtil.getCookiesAsArray(cookies);
    Assertions.assertEquals(0, result.length);

    cookies.put("KEY1", "VALUE1");
    cookies.put("KEY2", "VALUE2");
    result = CookieUtil.getCookiesAsArray(cookies);
    Assertions.assertEquals(2, result.length);
    Assertions.assertEquals("KEY1=VALUE1", result[0]);
    Assertions.assertEquals("KEY2=VALUE2", result[1]);
  }

  @Test
  void testGetCookiesMultiValueMap() {
    Map<String, String> cookies = new TreeMap<>();
    MultiValueMap<String, String> result = CookieUtil.getCookiesAsMultiValueMap(cookies);
    Assertions.assertEquals(0, result.size());

    cookies.put("KEY1", "VALUE1");
    cookies.put("KEY2", "VALUE2");
    result = CookieUtil.getCookiesAsMultiValueMap(cookies);
    Assertions.assertEquals(2, result.size());
    Assertions.assertEquals("VALUE1", result.get("KEY1").get(0));
    Assertions.assertEquals("VALUE2", result.get("KEY2").get(0));
  }
}