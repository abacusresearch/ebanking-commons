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
    Assertions.assertEquals("BANK_ID", getElement(BANK_ID_PATTERN, "b=BANK_ID!l=LANGUAGE!u=INTERFACE_URL?PARAM=üצה!se=SESSION_ID"));
    Assertions.assertEquals("LANGUAGE", getElement(TransportDataUtil.LANGUAGE_PATTERN, "b=BANK_ID!l=LANGUAGE!u=INTERFACE_URL?PARAM=üצה!se=SESSION_ID"));
  }

  @Test
  void testGetKeyValuePair(){
    Assertions.assertEquals("", getKeyValuePair(BANK_ID, ""));
    Assertions.assertEquals("!b=VALUE", getKeyValuePair(BANK_ID, "VALUE"));
    Assertions.assertEquals("!b=VALUE", getKeyValuePair(BANK_ID, "VALUE",false));
    Assertions.assertEquals("b=VALUE", getKeyValuePair(BANK_ID, "VALUE",true));
  }
}