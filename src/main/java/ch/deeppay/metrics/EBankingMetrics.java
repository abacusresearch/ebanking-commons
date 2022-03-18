package ch.deeppay.metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import ch.deeppay.util.FileFormat;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EBankingMetrics extends AbstractMetrics {

  @Autowired
  public EBankingMetrics(final MeterRegistry meterRegistry) {
    super(meterRegistry);
  }

  public void incrementStatementSuccessfulCounter(@Nonnull final String bankId,
                                                  @Nullable final ClientType clientType,
                                                  @Nonnull final String clientId,
                                                  @Nullable final FileFormat format) {
    Counter.builder(MetricConst.EBANKING_STATEMENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .tag(MetricConst.TAG_FORMAT, nonNull(format))
           .description(MetricConst.EBANKING_STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementPaymentSuccessfulCounter(@Nonnull final String bankId,
                                                @Nullable final ClientType clientType,
                                                @Nonnull final String clientId,
                                                @Nullable final FileFormat format) {
    Counter.builder(MetricConst.EBANKING_PAYMENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .tag(MetricConst.TAG_FORMAT, nonNull(format))
           .description(MetricConst.EBANKING_PAYMENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginCounter(@Nonnull final String bankId,
                                    @Nullable final ClientType clientType,
                                    @Nonnull final String clientId) {
    Counter.builder(MetricConst.EBANKING_LOGIN_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.EBANKING_LOGIN_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginSuccessfulCounter(@Nonnull final String bankId,
                                              @Nullable final ClientType clientType,
                                              @Nonnull final String clientId) {
    Counter.builder(MetricConst.EBANKING_LOGIN_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.EBANKING_LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementEventSuccessfulCounter(@Nonnull final String bankId,
                                                @Nullable final ClientType clientType,
                                                @Nonnull final String clientId,
                                                @Nullable final FileFormat format) {
    Counter.builder(MetricConst.EBANKING_EVENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .tag(MetricConst.TAG_FORMAT, nonNull(format))
           .description(MetricConst.EBANKING_EVENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

}