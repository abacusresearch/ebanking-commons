package ch.deeppay.secrets;

import javax.annotation.PostConstruct;

import ch.deeppay.metrics.CertificateMetrics;
import ch.deeppay.util.MtlsUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration()
public class SecretConfiguration {

  private final SecretProperties secretProperties;
  private final CertificateMetrics metrics;
  private final MtlsUtil mtlsUtil;

  public SecretConfiguration(final SecretProperties secretProperties, final CertificateMetrics metrics) {
    this.secretProperties = secretProperties;
    this.metrics = metrics;
    this.mtlsUtil = new MtlsUtil();
  }

  @PostConstruct
  private void initMetrics(){
    metrics.register(secretProperties.getSecrets());
  }

  @Bean
  @ConditionalOnMissingBean
  public SecurityProvider securityProvider() {
    return new SecurityProvider(secretProperties,mtlsUtil);
  }
}