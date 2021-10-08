package ch.deeppay.spring.logging;

import brave.baggage.BaggageField;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import static brave.baggage.CorrelationScopeConfig.SingleCorrelationField.newBuilder;

@Component
@ConditionalOnProperty(value = "ch.deeppay.spring.sessiontrace.enabled", matchIfMissing = true)
public class SleuthBaggageMDCConfiguration {

  public static final String X_B3_SESSION_TRACE_ID = "X-B3-SessionTraceId";
  public static final String SESSION_TRACE_ID_MDC_NAME = "sessionTraceId";
  public static final String X_CLIENT_TYPE = "X-ClientType";
  public static final String CLIENT_TYPE_MDC_NAME = "clientType";

  @Bean(name = "BaggageFieldClientType")
  @ConditionalOnMissingBean
  BaggageField clientType() {
    return BaggageField.create(X_CLIENT_TYPE);
  }

  @Bean(name = "BaggageFieldSessionTraceId")
  @ConditionalOnMissingBean
  BaggageField sessionTraceId() {
    return BaggageField.create(X_B3_SESSION_TRACE_ID);
  }

  @Bean
  @ConditionalOnMissingBean
  CurrentTraceContext.ScopeDecorator mdcScopeDecorator() {
    return MDCScopeDecorator.newBuilder()
                            .clear()
                            .add(newBuilder(clientType()).name(CLIENT_TYPE_MDC_NAME)
                                                         .flushOnUpdate()
                                                         .build())
                            .add(newBuilder(sessionTraceId()).name(SESSION_TRACE_ID_MDC_NAME)
                                                             .flushOnUpdate()
                                                             .build())
                            .build();
  }

}