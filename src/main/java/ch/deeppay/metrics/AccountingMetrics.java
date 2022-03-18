package ch.deeppay.metrics;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountingMetrics extends AbstractMetrics {


  @Autowired
  public AccountingMetrics(final MeterRegistry meterRegistry) {
    super(meterRegistry);
  }

  public void incrementStatementSuccessfulCounter(@Nonnull final String bankId,
                                                  @Nullable final ClientType clientType,
                                                  @Nonnull final String clientId) {
    Counter.builder(MetricConst.ACCOUNTING_STATEMENT_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.ACCOUNTING_STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginCounter(@Nonnull final String bankId,
                                    @Nullable final ClientType clientType,
                                    @Nonnull final String clientId) {
    Counter.builder(MetricConst.ACCOUNTING_LOGIN_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.ACCOUNTING_LOGIN_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }

  public void incrementLoginSuccessfulCounter(@Nonnull final String bankId,
                                              @Nullable final ClientType clientType,
                                              @Nonnull final String clientId) {
    Counter.builder(MetricConst.ACCOUNTING_LOGIN_SUCCESSFUL_COUNTER)
           .tag(MetricConst.TAG_BANK_ID, nonEmpty(bankId))
           .tag(MetricConst.TAG_CLIENT_TYPE, nonNull(clientType))
           .tag(MetricConst.TAG_CLIENT_ID, convertClientId(clientId))
           .description(MetricConst.ACCOUNTING_LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION)
           .register(meterRegistry).increment();
  }
}