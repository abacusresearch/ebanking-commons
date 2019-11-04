package ch.deeppay.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TransportDataUtilTest {

  @Test
  void encrypt() throws Exception {
    String secret = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    String salt = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

    String transportData = "b=testtest!a=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!r=rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!i=iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii!is=ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";

    String encrypt = TransportDataUtil.encrypt(transportData, secret, salt);
    String encryptTwice = TransportDataUtil.encrypt(transportData, secret, salt);

    Assertions.assertNotNull(encrypt);
    Assertions.assertNotNull(encryptTwice);
    Assertions.assertEquals(encrypt, encryptTwice);
  }

  @Test
  void decrypt() {
    String secret = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    String salt = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

    String encrypt = "eGgedBv87cL2t0h7dNuxfDyYWFzT1i2FQPWEpEQjTu7ztXxrq0iSau7y2J8uaB0Nzm37HOYdoDRZLnZIPmB3ivDn/3vNbXEk6Zw9YIeiiHZnbf+HVO3VPf+DH4yIZisEpQVu/PBYpzqHQ+hdDtUXoa0YXVxvsdKnhjpj+rrTeAV8Vu9iCuAbncmY/3KDq6walVR6tWzKKk91zxFB9RDODiUzmlfCi6TcXppgZBUzXT+ZcXLTU2pZNDL4aWOkyZFk5MTsouOyl6X0C44OMtRo56537deQrGyWqv/L1TTNgYQ6FZqKYvznGoUJgfXXCN6M9hgEjh+D99v8EV0wb9Oijw==";
    String transportData = "b=testtest!a=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!r=rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!i=iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii!is=ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";

    String decrypt = TransportDataUtil.decrypt(encrypt, secret, salt);
    Assertions.assertEquals(transportData, decrypt);
  }

}