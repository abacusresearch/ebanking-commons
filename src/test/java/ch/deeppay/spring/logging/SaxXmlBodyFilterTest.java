package ch.deeppay.spring.logging;

import com.google.common.collect.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class SaxXmlBodyFilterTest {

  private static final String ENVELEOP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ns1:Logon1TanRequest xmlns:ns1=\"http://elink.zkb.ch/v3_0/xsd\"><sprachCode>DE</sprachCode><vertragnummer>700-1111</vertragnummer><passwort>123456</passwort><software>deeppay</software><version>1.0.0</version></ns1:Logon1TanRequest></soapenv:Body></soapenv:Envelope>";
  private static final String REF_ENVELEOP = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header/><soapenv:Body><ns1:Logon1TanRequest xmlns:ns1=\"http://elink.zkb.ch/v3_0/xsd\"><sprachCode>DE</sprachCode><vertragnummer>[secret]</vertragnummer><passwort>[secret]</passwort><software>deeppay</software><version>1.0.0</version></ns1:Logon1TanRequest></soapenv:Body></soapenv:Envelope>";

  @Test
  void testFilter() {
    SaxXmlBodyFilter filter = new SaxXmlBodyFilter("[secret]", Sets.newHashSet("vertragnummer", "passwort"));
    Assertions.assertEquals(REF_ENVELEOP, filter.filter(MediaType.TEXT_XML_VALUE, ENVELEOP));
  }

  @Test
  void testFilterWrongMediaType() {
    SaxXmlBodyFilter filter = new SaxXmlBodyFilter("[secret]", Sets.newHashSet("vertragnummer", "passwort"));
    Assertions.assertEquals(ENVELEOP, filter.filter(MediaType.TEXT_PLAIN_VALUE, ENVELEOP));
  }


}