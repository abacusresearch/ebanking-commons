package ch.deeppay.sentry;

import io.sentry.Sentry;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class SentryConfiguration implements BeanPostProcessor {

  public SentryConfiguration(@Value("${sentry.dsn}") final String sentryDsn) {
    Sentry.init(sentryDsn);
    log.debug("sentry: {}", sentryDsn);
  }

  @Bean
  public HandlerExceptionResolver sentryExceptionResolver() {
    return new SentryExceptionResolver();
  }


}
