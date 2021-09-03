package ch.deeppay.encryption;

import javax.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration()
@ConditionalOnProperty(value = "transportdata.encryption.enabled", matchIfMissing = false)
public class EncryptionConfiguration {

  private final String secret;
  private final String salt;

  public EncryptionConfiguration(@Value("${transportdata.encryption.secret}") @Nonnull final String secret,
                                 @Value("${transportdata.encryption.salt}") @Nonnull final String salt) {
    this.secret = secret;
    this.salt = salt;
  }

  @Bean
  DataEncryptor dataEncryptor() {
    return new DataEncryption(secret, salt);
  }

}
