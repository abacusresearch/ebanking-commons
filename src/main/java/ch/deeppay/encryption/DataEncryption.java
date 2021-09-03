package ch.deeppay.encryption;

import javax.annotation.Nonnull;

import ch.deeppay.util.EncryptionUtil;
import org.apache.commons.lang.StringUtils;

public class DataEncryption implements DataEncryptor {

  private final String secret;
  private final String salt;

  public DataEncryption(@Nonnull final String secret, @Nonnull final String salt) {
    this.secret = secret;
    this.salt = salt;
  }

  @Override
  @Nonnull
  public String encrypt(@Nonnull final String transportData) {
    return EncryptionUtil.encrypt(transportData, secret, salt).orElse(StringUtils.EMPTY);
  }

  @Override
  @Nonnull
  public String decrypt(@Nonnull final String transportData) {
    if (StringUtils.isEmpty(transportData)) {
      return StringUtils.EMPTY;
    }
    return EncryptionUtil.decrypt(transportData, secret, salt).orElse(StringUtils.EMPTY);
  }
}
