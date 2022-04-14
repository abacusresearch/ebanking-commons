package ch.deeppay.sentry;

import ch.deeppay.spring.logging.SleuthBaggageMDCConfiguration;
import io.sentry.Sentry;
import io.sentry.SentryClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.HandlerExceptionResolver;

import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

/**
 * BeanPostProcessor interface is only used that the bean is initialized before other normal beans
 */
@Log4j2
@Configuration
@Order(HIGHEST_PRECEDENCE)
@ConditionalOnProperty(name = "sentry.dsn")
public class SentryConfiguration implements BeanPostProcessor, Ordered {

  private final String eventLevel;

  public SentryConfiguration(@Value("${sentry.dsn}") final String sentryDsn, @Value("${sentry.exception.resolver.event.level: INFO}") final String eventLevel) {
    this.eventLevel = eventLevel;
    SentryClient client = Sentry.init(sentryDsn);
    client.addMdcTag(SleuthBaggageMDCConfiguration.SESSION_TRACE_ID_MDC_NAME);

    log.debug("sentry: {}", sentryDsn);
  }

  @Bean
  @Conditional(value=EventLevelConditionOnProperty .class)
  public HandlerExceptionResolver sentryExceptionResolver() {
    return new SentryExceptionResolver(eventLevel);
  }

  @Override
  public int getOrder() {
    return HIGHEST_PRECEDENCE; // ensure this bean is created first that SentryClient is initialized for SentryAppender as well.
  }
}
