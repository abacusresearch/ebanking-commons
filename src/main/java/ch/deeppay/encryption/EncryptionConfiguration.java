package ch.deeppay.encryption;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;


@Configuration()
@ConditionalOnProperty(value = "transportdata.encryption.enabled", matchIfMissing = false)
public class EncryptionConfiguration {

  private final String secret;
  private final String salt;

  public EncryptionConfiguration(@Value("${transportdata.encryption.secret}") @NonNull final String secret,
                                 @Value("${transportdata.encryption.salt}") @NonNull final String salt) {
    this.secret = secret;
    this.salt = salt;
  }

  @Bean
  DataEncryptor dataEncryptor() {
    return new DataEncryption(secret, salt);
  }

}
