package ch.deeppay.util;

import lombok.extern.log4j.Log4j2;

import javax.annotation.Nonnull;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Optional;

/**
 * Helper class to encrypt and decrypt transport data using AES256 algorithm.
 */
@Log4j2
public class EncryptionUtil {

  private static final String CIPHER_PADDING = "AES/CBC/PKCS5PADDING";
  private static final String SECRET_ALGORITHM = "PBKDF2WithHmacSHA256";
  private static final String ENCRYPT_STANDARD = "AES";
  private static final int ENCRYPT_LENGTH = 256;
  private static final int ITERATIONS = 65536;

  @Nonnull
  public static Optional<String> encrypt(@Nonnull final String strToEncrypt, @Nonnull final String secret, @Nonnull final String salt) {
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_ALGORITHM);
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), ITERATIONS, ENCRYPT_LENGTH);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ENCRYPT_STANDARD);
      Cipher cipher = Cipher.getInstance(CIPHER_PADDING);

      cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivspec);
      String base64Encoded = Base64.getEncoder().encodeToString(cipher.doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
      return Optional.of(URLEncoder.encode(base64Encoded, StandardCharsets.UTF_8.name()));
    } catch (Exception e) {
      log.error("Error while decrypting: {}", e);
    }
    return Optional.empty();
  }

  @Nonnull
  public static Optional<String> decrypt(@Nonnull final String strToDecrypt, @Nonnull final String secret, @Nonnull final String salt) {
    try {
      byte[] iv = {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
      IvParameterSpec ivspec = new IvParameterSpec(iv);

      SecretKeyFactory factory = SecretKeyFactory.getInstance(SECRET_ALGORITHM);
      KeySpec spec = new PBEKeySpec(secret.toCharArray(), salt.getBytes(), ITERATIONS, ENCRYPT_LENGTH);
      SecretKey tmp = factory.generateSecret(spec);
      SecretKeySpec secretKey = new SecretKeySpec(tmp.getEncoded(), ENCRYPT_STANDARD);

      Cipher cipher = Cipher.getInstance(CIPHER_PADDING);
      cipher.init(Cipher.DECRYPT_MODE, secretKey, ivspec);
      String base64Encoded = urlDecode(strToDecrypt);
      byte[] decode = Base64.getDecoder().decode(base64Encoded);
      return Optional.of(new String(cipher.doFinal(decode)));
    } catch (Exception e) {
      log.error("Error while decrypting: {}", e);
    }
    return Optional.empty();
  }

  private static String urlDecode(@Nonnull String strToDecrypt) throws UnsupportedEncodingException {
    String decode = URLDecoder.decode(strToDecrypt, StandardCharsets.UTF_8.name());

    if (org.apache.commons.codec.binary.Base64.isBase64(decode) && !decode.contains(" ")) {
      return decode;
    }
    return strToDecrypt;
  }

}
