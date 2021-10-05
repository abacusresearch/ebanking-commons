package ch.deeppay.models.transportdata;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ch.deeppay.models.transportdata.TransportDataUtil.BANK_ID;
import static ch.deeppay.models.transportdata.TransportDataUtil.BANK_ID_PATTERN;
import static ch.deeppay.models.transportdata.TransportDataUtil.getElement;
import static ch.deeppay.models.transportdata.TransportDataUtil.getKeyValuePair;

public class TransportDataUtilTest {

  @Test
  void testGetElement(){
    Assertions.assertEquals("", getElement(BANK_ID_PATTERN, ""));
    Assertions.assertEquals("", getElement(BANK_ID_PATTERN, "bb=BANK_ID!l="));
    Assertions.assertEquals("", getElement(BANK_ID_PATTERN, "=BANK_ID!l="));
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "b=BANK_ID!l="));
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "bb=VALUE!ba=VALUE2!ab=VALUE3!b=BANK_ID"));
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "b=BANK_ID"));
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "b=BANK_ID!"));
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "b=BANK_ID!l=LANGUAGE!u=INTERFACE_URL?PARAM=���!se=SESSION_ID"));
    Assertions.assertEquals("LANGUAGE", getElement(TransportDataUtil.LANGUAGE_PATTERN, "b=BANK_ID!l=LANGUAGE!u=INTERFACE_URL?PARAM=���!se=SESSION_ID"));
  }

  @Test
  void testGetKeyValuePair(){
    Assertions.assertEquals("", getKeyValuePair(BANK_ID, ""));
    Assertions.assertEquals("!b=VALUE", getKeyValuePair(BANK_ID, "VALUE"));
    Assertions.assertEquals("!b=VALUE", getKeyValuePair(BANK_ID, "VALUE",false,false));
    Assertions.assertEquals("b=VALUE", getKeyValuePair(BANK_ID, "VALUE",true,false));
  }


  @Test
  void testGetKeyValuePairEncoded(){
    Assertions.assertEquals("b=Test%3Dabcd%21efg%24%C3%A4%21%C3%A8%26%25%C3%A7f", getKeyValuePair(BANK_ID, "Test=abcd!efg$�!�&%�f",true,true));
  }

  @Test
  void testGetElementDecode(){
    Assertions.assertEquals("Test=abcd!efg$�!�&%�f", getElement(BANK_ID_PATTERN, "b=Test%3Dabcd%21efg%24%C3%A4%21%C3%A8%26%25%C3%A7f",true));
  }
  
}