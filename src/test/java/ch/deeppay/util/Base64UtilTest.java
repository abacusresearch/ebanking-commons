package ch.deeppay.util;

import java.nio.charset.StandardCharsets;

import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Base64UtilTest {

  @Test
  void testIsBase64() {
      Assertions.assertFalse(Base64Util.isBase64(""));
      Assertions.assertFalse(Base64Util.isBase64(null));
      Assertions.assertFalse(Base64Util.isBase64("Hello World"));
      Assertions.assertTrue(Base64Util.isBase64("UEsDBBQACAgIAGNHx1QAAAAAAAAAAAAAAAAEAAAAZGF0YbNxzs8rSc0rsQMAUEsHCA22zw4LAAAACQAAAFBLAQIUABQACAgIAGNHx1QNts8OCwAAAAkAAAAEAAAAAAAAAAAAAAAAAAAAAABkYXRhUEsFBgAAAAABAAEAMgAAAD0AAAAAAA=="));
      Assertions.assertTrue(Base64Util.isBase64(Base64.encodeBase64String("Hello world <>".getBytes(StandardCharsets.UTF_8))));
  }
}