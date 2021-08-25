package ch.deeppay.spring.logging;

import brave.baggage.BaggageField;
import brave.baggage.CorrelationScopeConfig;
import brave.context.slf4j.MDCScopeDecorator;
import brave.propagation.CurrentTraceContext;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(value = "ch.deeppay.spring.sessiontrace.enabled", matchIfMissing = true)
public class SessionTraceIdConfig {

  public static final String X_B3_SESSION_TRACE_ID = "X-B3-SessionTraceId";

  @Bean
  @ConditionalOnMissingBean
  BaggageField sessionTraceId() {
    return BaggageField.create(X_B3_SESSION_TRACE_ID);
  }

  @Bean
  @ConditionalOnMissingBean
  CurrentTraceContext.ScopeDecorator mdcScopeDecorator() {
    return MDCScopeDecorator.newBuilder()
                            .clear()
                            .add(CorrelationScopeConfig.SingleCorrelationField.newBuilder(sessionTraceId())
                                                                              .flushOnUpdate()
                                                                              .build())
                            .build();
  }

}