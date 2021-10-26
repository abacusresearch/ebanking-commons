package ch.deeppay.metrics;

import java.util.Arrays;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

class TestMetricsUtil {

  @Test
  void testGetClientIdFromHttpHeaders(){
    HttpHeaders headers = new HttpHeaders();
    Assertions.assertEquals("",MetricsUtil.getClientId(headers));

    headers.put(MetricsUtil.HTTP_HEADER_ABACUS_LICENSE, Arrays.asList("ABACUS_LICENCE"));
    Assertions.assertEquals("ABACUS_LICENCE",MetricsUtil.getClientId(headers));

    headers.put(MetricsUtil.HTTP_HEADER_ABANINJA, Arrays.asList("NINJA_HEADER"));
    Assertions.assertEquals("NINJA_HEADER",MetricsUtil.getClientId(headers));
  }

  @Test
  void testGetClientTypeFromHttpHeaders(){
    HttpHeaders headers = new HttpHeaders();
    Assertions.assertEquals(ClientType.UNKNOWN,MetricsUtil.getClientType(headers));

    headers.put(MetricsUtil.HTTP_HEADER_ABACUS, Arrays.asList("ABACUS_LICENCE"));
    Assertions.assertEquals(ClientType.ABACUS_G4,MetricsUtil.getClientType(headers));

    headers.put(MetricsUtil.HTTP_HEADER_ABANINJA, Arrays.asList("NINJA_HEADER"));
    Assertions.assertEquals(ClientType.ABANINJA,MetricsUtil.getClientType(headers));
  }

  @Test
  void testGetClientTypeFromHttpHeadersWithUserAgent(){
    HttpHeaders headers = new HttpHeaders();
    Assertions.assertEquals(ClientType.UNKNOWN,MetricsUtil.getClientType(headers));

    headers.put(HttpHeaders.USER_AGENT, Arrays.asList("ABACUS_G4/2021"));
    Assertions.assertEquals(ClientType.ABACUS_G4,MetricsUtil.getClientType(headers));

    headers.put(HttpHeaders.USER_AGENT, Arrays.asList("ABANINJA1234"));
    Assertions.assertEquals(ClientType.ABANINJA,MetricsUtil.getClientType(headers));

    headers.put(HttpHeaders.USER_AGENT, Arrays.asList("POSTMAN"));
    Assertions.assertEquals(ClientType.POSTMAN,MetricsUtil.getClientType(headers));
  }

}