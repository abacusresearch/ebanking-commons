package ch.deeppay.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

class EncryptionUtilTest {

  @Test
  void encrypt() throws Exception {
    String secret = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    String salt = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

    String transportData = "b=testtest!a=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!r=rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!i=iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii!is=ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";

    Optional<String> encrypt = EncryptionUtil.encrypt(transportData, secret, salt);
    Optional<String> encryptTwice = EncryptionUtil.encrypt(transportData, secret, salt);

    Assertions.assertTrue(encrypt.isPresent());
    Assertions.assertTrue(encryptTwice.isPresent());

    Assertions.assertEquals(encrypt.get(), encryptTwice.get());
  }

  @Test
  void decrypt() {
    String secret = "BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB";
    String salt = "CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC";

    String encrypt = "eGgedBv87cL2t0h7dNuxfDyYWFzT1i2FQPWEpEQjTu7ztXxrq0iSau7y2J8uaB0Nzm37HOYdoDRZLnZIPmB3ivDn%2F3vNbXEk6Zw9YIeiiHZnbf%2BHVO3VPf%2BDH4yIZisEpQVu%2FPBYpzqHQ%2BhdDtUXoa0YXVxvsdKnhjpj%2BrrTeAV8Vu9iCuAbncmY%2F3KDq6walVR6tWzKKk91zxFB9RDODiUzmlfCi6TcXppgZBUzXT%2BZcXLTU2pZNDL4aWOkyZFk5MTsouOyl6X0C44OMtRo56537deQrGyWqv%2FL1TTNgYQ6FZqKYvznGoUJgfXXCN6M9hgEjh%2BD99v8EV0wb9Oijw%3D%3D";
    String transportData = "b=testtest!a=aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa!r=rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr!i=iiiiiiiiiiiiiiiiiiiiiiiiiiiiiii!is=ssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss";

    Optional<String> decrypt = EncryptionUtil.decrypt(encrypt, secret, salt);
    Assertions.assertTrue(decrypt.isPresent());
    Assertions.assertEquals(transportData, decrypt.get());
  }

  @Test
  void decrypt_urlencoded() {
    String secret = "123456789abcdefghijklmnopqrstuvwxyz";
    String salt = "12121212121212121212121212121212";
    String encrypt = "G8b2LoHhPEQLHXO7VI1VOZEAIPx51lnu2m00UQ9Uv17ENj6IuLgSNyuHU53++DbDH1zmXIaa/vaBhTm4WhylYfY1MiV1v/7+q4/rGsFUNHGF8hNe4RUk/AHYmEQsqjjpFEJJ0eU5WlaafkKAjbfqyFyEJfR7meuoBl5+BK9p0/DlQrXWPDNKDMBi2ShS0G6TOtAxvJrtSeD/aRGio2VK4RKnScqOFVHIyTA0/1XgycFOVT1bd/i7E+hfC0wxmdz06qZO4kEOhrJ0k2Dpv4Ko5wRTla2s1KvHUy4Rqmj3WnuqZVBxvjtBnaRAHYXLLzFA";

    Optional<String> decrypt = EncryptionUtil.decrypt(encrypt, secret, salt);
    Assertions.assertTrue(decrypt.isPresent());
    Assertions.assertNotNull(decrypt.get());
  }

}