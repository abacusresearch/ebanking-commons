package ch.deeppay.util;

import lombok.extern.log4j.Log4j2;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Nonnull;
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
import java.util.Optional;

@Component
@Log4j2
public class MtlsUtil {

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

    final byte[] deeppayCertBytes = Base64.getDecoder().decode(certificate);
    final byte[] bankCertBytes = Base64.getDecoder().decode(bankcertificate);
    final byte[] keyBytes = Base64.getDecoder().decode(privatekey);

    try {
      final X509Certificate deepPayCert = generateCertificateFromDER(deeppayCertBytes);
      final X509Certificate customerCert = generateCertificateFromDER(bankCertBytes);
      final PrivateKey key = generatePrivateKeyFromDER(keyBytes);

      final KeyStore keystore = KeyStore.getInstance("JKS");
      final char[] password = secret.toCharArray();

      keystore.load(null, password);
      keystore.setCertificateEntry("cert-alias", deepPayCert);
      keystore.setKeyEntry("key-alias", key, password, new Certificate[]{deepPayCert});

      final KeyStore truststore = KeyStore.getInstance("JKS");
      truststore.load(null, password);
      truststore.setCertificateEntry("bank", customerCert);

      final SSLContext sslContext = SSLContextBuilder
          .create()
          .loadKeyMaterial(keystore, password)
          .loadTrustMaterial(truststore, new TrustSelfSignedStrategy())
          .build();

      final HttpClient httpClient = HttpClientBuilder.create()
          .setSSLContext(sslContext)
          .build();

      final ClientHttpRequestFactory requestFactory =
          new HttpComponentsClientHttpRequestFactory(httpClient);

      log.info("Using MTLS rest template");

      return Optional.of(requestFactory);

    } catch (CertificateException | InvalidKeySpecException | NoSuchAlgorithmException | KeyStoreException | IOException | UnrecoverableKeyException | KeyManagementException e) {
      log.error("MTLS Rest Template Error", e);
    }

    return Optional.empty();
  }

  private PrivateKey generatePrivateKeyFromDER(byte[] keyBytes) throws InvalidKeySpecException, NoSuchAlgorithmException {
    final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
    final KeyFactory keyFactory = KeyFactory.getInstance("RSA");
    return keyFactory.generatePrivate(keySpec);
  }

  private X509Certificate generateCertificateFromDER(byte[] certBytes) throws CertificateException {
    final CertificateFactory factory = CertificateFactory.getInstance("X.509");
    return (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(certBytes));
  }
}
