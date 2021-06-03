package ch.deeppay.metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;
import java.util.Set;

import ch.deeppay.util.FileFormat;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EBankingMetrics {

  private static final String DEMO  = "Demo";

  private final MeterRegistry meterRegistry;

  @Value("${ch.deeppay.metrics.demo_ids:17,18,77}")
  private Set<String> demoSet;


  @Autowired
  public EBankingMetrics(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }

  public void incrementStatementSuccessfulCounter(@Nonnull final String bankId,
                                                  @Nullable final ClientType clientType,
                                                  @Nonnull final String clientId,
                                                  @Nullable final FileFormat format) {
    Counter.builder(MetricConst.STATEMENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .tag(MetricConst.TAG_FORMAT, nonNull(format))
           .description(MetricConst.STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementPaymentSuccessfulCounter(@Nonnull final String bankId,
                                                @Nullable final ClientType clientType,
                                                @Nonnull final String clientId,
                                                @Nullable final FileFormat format) {
    Counter.builder(MetricConst.PAYMENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .tag(MetricConst.TAG_FORMAT, nonNull(format))
           .description(MetricConst.PAYMENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginCounter(@Nonnull final String bankId,
                                    @Nullable final ClientType clientType,
                                    @Nonnull final String clientId) {
    Counter.builder(MetricConst.LOGIN_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.LOGIN_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginSuccessfulCounter(@Nonnull final String bankId,
                                              @Nullable final ClientType clientType,
                                              @Nonnull final String clientId) {
    Counter.builder(MetricConst.LOGIN_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  private String convertClientId(String clientId){
    return Objects.nonNull(demoSet) && demoSet.contains(clientId) ? DEMO : StringUtils.EMPTY;
  }

  private String nonNull(ClientType clientType) {
    return Objects.isNull(clientType) ? ClientType.UNKNOWN.toString() : clientType.toString();
  }

  private String nonEmpty(String bankId) {
    return StringUtils.isEmpty(bankId) ? "-1" : bankId;
  }

  private String nonNull(FileFormat fileFormat) {
    return Objects.isNull(fileFormat) ? "unknown" : fileFormat.toString();
  }

}