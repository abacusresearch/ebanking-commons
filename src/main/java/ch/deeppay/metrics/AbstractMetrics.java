package ch.deeppay.metrics;

import java.util.Objects;
import java.util.Set;

import ch.deeppay.util.FileFormat;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;

public class AbstractMetrics {

  static final String DEMO  = "Demo";

  protected final MeterRegistry meterRegistry;

  @Value("${ch.deeppay.metrics.demo_ids:17,18,77}")
  protected Set<String> demoSet;


  public AbstractMetrics(final MeterRegistry meterRegistry) {
    this.meterRegistry = meterRegistry;
  }


  protected String convertClientId(String clientId){
    return Objects.nonNull(demoSet) && demoSet.contains(clientId) ? DEMO : StringUtils.EMPTY;
  }

  protected String nonNull(ClientType clientType) {
    return Objects.isNull(clientType) ? ClientType.UNKNOWN.toString() : clientType.toString();
  }

  protected String nonEmpty(String bankId) {
    return StringUtils.isEmpty(bankId) ? "-1" : bankId;
  }

  protected String nonNull(FileFormat fileFormat) {
    return Objects.isNull(fileFormat) ? "unknown" : fileFormat.toString();
  }

}