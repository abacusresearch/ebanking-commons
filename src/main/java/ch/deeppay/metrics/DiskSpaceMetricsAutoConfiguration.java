package ch.deeppay.metrics;

import java.io.File;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.binder.jvm.DiskSpaceMetrics;
import org.springframework.boot.actuate.autoconfigure.metrics.CompositeMeterRegistryAutoConfiguration;
import org.springframework.boot.actuate.autoconfigure.metrics.MetricsAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration for disk space metrics
 */
@Configuration()
@ConditionalOnClass({MeterRegistry.class})
@ConditionalOnProperty(value = "management.metrics.binders.diskspace.enabled", matchIfMissing = false)
@AutoConfigureAfter({MetricsAutoConfiguration.class, CompositeMeterRegistryAutoConfiguration.class})
public class DiskSpaceMetricsAutoConfiguration {

  @Bean
  @ConditionalOnMissingBean
  public DiskSpaceMetrics diskSpaceMetrics() {
    return new DiskSpaceMetrics(new File("."));
  }

}