package ch.deeppay.metrics;

import javax.annotation.Nullable;
import java.io.ByteArrayInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import ch.deeppay.models.vault.Secret;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component()
public class CertificateMetrics {

  private final MeterRegistry meterRegistry;

  @Autowired
  public CertificateMetrics(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void register(Map<String, Secret> secrets){
    for(Map.Entry<String,Secret> entry : secrets.entrySet()){
      register(entry.getKey(),entry.getValue().getCertificate());
      register(entry.getKey(),entry.getValue().getBankCertificate());
    }
  }


  /**
   * Register cert metrics to monitor the expiry date of the passed certificate
   * @param base64Certificate base64 string of a certificate as "DER" format
   */
  private void register(String bankId, String base64Certificate) {
    if(StringUtils.isEmpty(base64Certificate)){
      return;
    }

    try {
      final CertificateFactory factory = CertificateFactory.getInstance("X.509");
      final X509Certificate certificate = (X509Certificate) factory.generateCertificate(new ByteArrayInputStream(Base64.getDecoder()
                                                                                                                       .decode(base64Certificate)));
      final Date notAfter = certificate.getNotAfter();
      final Number expiresAfter = notAfter.getTime();
      final JcaX509CertificateHolder certificateHolder = new JcaX509CertificateHolder(certificate);
      final X500Name x500Name = certificateHolder.getSubject();
      final String subjectName = getRDN(x500Name, BCStyle.CN);
      final String subjectOrganization = getRDN(x500Name,BCStyle.O);

      Gauge.builder(MetricConst.CERT_EXPIRES_IN_SECONDS, () -> Duration.between(Instant.now(),notAfter.toInstant()).getSeconds())
           .tag(MetricConst.TAG_BANK_ID, bankId)
           .tag(MetricConst.TAG_SUBJECT_NAME, subjectName)
           .tag(MetricConst.TAG_SUBJECT_ORGANIZATION, subjectOrganization)
           .description(MetricConst.CERT_EXPIRES_IN_SECONDS_DESCRIPTION)
           .register(meterRegistry);

      Gauge.builder(MetricConst.CERT_EXPIRES_AFTER_TIMESTAMP, () -> expiresAfter)
           .tag(MetricConst.TAG_BANK_ID, bankId)
           .tag(MetricConst.TAG_SUBJECT_NAME, subjectName)
           .tag(MetricConst.TAG_SUBJECT_ORGANIZATION, subjectOrganization)
           .description(MetricConst.CERT_EXPIRES_AFTER_TIMESTAMP_DESCRIPTION)
           .register(meterRegistry);

    } catch (Exception e) {
      log.error(e);
      Counter.builder(MetricConst.CERT_ERROR_COUNTER)
             .tag(MetricConst.TAG_BANK_ID, bankId)
             .description(MetricConst.CERT_ERROR_COUNTER_DESCRIPTION)
             .register(meterRegistry).increment();
    }
  }

  private String getRDN(@Nullable final X500Name name, ASN1ObjectIdentifier objectIdentifier){
    if(Objects.isNull(name) || ArrayUtils.isEmpty(name.getRDNs(objectIdentifier))){
      return StringUtils.EMPTY;
    }
    RDN cn = name.getRDNs(objectIdentifier)[0];
    return IETFUtils.valueToString(cn.getFirst().getValue());
  }

}