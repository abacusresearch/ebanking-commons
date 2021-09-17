package ch.deeppay.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.net.ssl.SSLContext;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Objects;
import java.util.Optional;

import ch.deeppay.models.vault.Secret;
import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class MtlsUtil {

  private static final String KEY_STORE_TYPE = "JKS";
  private static final String ALGORITHM = "RSA";
  private static final String CERTIFICATE = "X.509";

  @Nonnull
  public RestTemplate getMtlsRestTemplate(@Nonnull final String privatekey, @Nonnull final String certificate,
                                          @Nonnull final String bankcertificate, @Nonnull final String secret) {

    final RestTemplate restTemplate = new RestTemplate();
    getMtlsRequestFactory(privatekey, certificate, bankcertificate, secret).ifPresent(restTemplate::setRequestFactory);
    return restTemplate;
  }

  @Nonnull
  public Optional<ClientHttpRequestFactory> getMtlsRequestFactory(@Nonnull final String privatekey, @Nonnull final String certificate,
                                                                  @Nonnull final String bankcertificate, @Nonnull final String secret) {

    final SSLContext sslContext = getSSLContext(privatekey, certificate, bankcertificate, secret);
    if (Objects.isNull(sslContext)) {
      return Optional.empty();
    }

    final HttpClient httpClient = HttpClientBuilder.create()
                                                   .setSSLContext(sslContext)
                                                   .build();

    final ClientHttpRequestFactory requestFactory =
        new HttpComponentsClientHttpRequestFactory(httpClient);

    log.info("Using MTLS rest template");
    return Optional.of(requestFactory);

  }

  @Nullable
  public SSLContext getSSLContext(final Secret secret, final String trustStorePassword) {
    return getSSLContext(secret.getPrivateKey(), secret.getCertificate(), secret.getBankCertificate(), trustStorePassword);
  }

  @Nullable
  public SSLContext getSSLContext(final String privateKey, final String certificate,
                                  final String bankCertificate, final String trustStorePassword) {
    final byte[] keyBytes = Base64.getDecoder().decode(privateKey);
    final byte[] deepPayCertBytes = Base64.getDecoder().decode(certificate);
    final byte[] bankCertBytes = Base64.getDecoder().decode(bankCertificate);

    try {
      final X509Certificate deepPayCert = generateCertificateFromDER(deepPayCertBytes);
      final X509Certificate customerCert = generateCertificateFromDER(bankCertBytes);
      final PrivateKey key = generatePrivateKeyFromDER(keyBytes);

      final KeyStore keystore = KeyStore.getInstance(KEY_STORE_TYPE);
      final char[] passwordAsChar = trustStorePassword.toCharArray();

      keystore.load(null, passwordAsChar);
      keystore.setCertificateEntry("cert-alias", deepPayCert);
      keystore.setKeyEntry("key-alias", key, passwordAsChar, new Certificate[]{deepPayCert});

      final KeyStore truststore = KeyStore.getInstance(KEY_STORE_TYPE);
      truststore.load(null, passwordAsChar);
      truststore.setCertificateEntry("bank", customerCert);

      return SSLContextBuilder
          .create()
          .loadKeyMaterial(keystore, passwordAsChar)
          .loadTrustMaterial(truststore, new TrustSelfSignedStrategy())
          .build();
    } catch (CertificateException | InvalidKeySpecException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | KeyManagementException e) {
      log.error("MTLS Rest Template Error", e);
    }
    return null;
  }


  private PrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
    final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    final KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
    return keyFactory.generatePrivate(keySpec);
  }

  private X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
    final CertificateFactory factory = CertificateFactory.getInstance(CERTIFICATE);
    return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
  }
}
