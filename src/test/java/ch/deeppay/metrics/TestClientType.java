package ch.deeppay.metrics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestClientType {

  @Test
  void testFromUserAgent(){
    Assertions.assertEquals(ClientType.UNKNOWN,ClientType.fromUserAgent("invalid"));
    Assertions.assertEquals(ClientType.ABACUS_G4, ClientType.fromUserAgent("Abacus-G4"));
    Assertions.assertEquals(ClientType.ABACUS_G4, ClientType.fromUserAgent("abacus-g4/1234"));
    Assertions.assertEquals(ClientType.ABANINJA, ClientType.fromUserAgent("abaninja/v1.2"));
    Assertions.assertEquals(ClientType.POSTMAN, ClientType.fromUserAgent("postman 1.2"));
  }

}