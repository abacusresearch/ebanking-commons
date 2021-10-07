package ch.deeppay.secrets;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import java.util.Objects;
import java.util.Optional;

import ch.deeppay.models.vault.Secret;
import ch.deeppay.util.MtlsUtil;
import lombok.extern.log4j.Log4j2;


@Log4j2
public class SecurityProvider {

  private final SecretProperties secretProperties;
  private final MtlsUtil mtlsUtil;

  public SecurityProvider(@Nullable final SecretProperties secretProperties, final MtlsUtil mtlsUtil) {
    this.secretProperties = secretProperties;
    this.mtlsUtil = mtlsUtil;
  }


  public Optional<Secret> getProperties(@Nonnull final String bankId) {
    if (Objects.nonNull(secretProperties) &&
        secretProperties.getSecrets().containsKey(bankId.toLowerCase())) {
      return Optional.of(secretProperties.getSecrets().get(bankId.toLowerCase()));
    }
    return Optional.empty();
  }

  @Nullable
  public SSLContext getSSLContext(@Nonnull final String bankId) {
    if (useMtlsConnection(bankId)) {
      return getProperties(bankId).map(secret -> mtlsUtil.getSSLContext(secret, "secret")).orElse(null);
    }
    return null;
  }

  private boolean useMtlsConnection(@Nonnull final String bankId) {
    return getProperties(bankId).map(Secret::hasCertificate).orElse(false);
  }

}
