package ch.deeppay.rest.async;

import ch.deeppay.metrics.EBankingMetrics;
import ch.deeppay.metrics.MetricTagValueGetter;
import ch.deeppay.models.ebanking.statement.StatementResponse;
import org.springframework.http.ResponseEntity;

import static ch.deeppay.metrics.MetricConst.EBANKING_STATEMENT_SUCCESSFUL_COUNTER;

public class StatementMetricCounter {

  private final EBankingMetrics metrics;
  private final MetricTagValueGetter metricTagValueGetter;

  public StatementMetricCounter(final EBankingMetrics metrics,
                                final MetricTagValueGetter metricTagValueGetter) {
    this.metrics = metrics;
    this.metricTagValueGetter = metricTagValueGetter;
  }

  public void count(final ResponseEntity<StatementResponse> result) {
    if (result.getStatusCode().is2xxSuccessful()) {
      metrics.incrementCounter(EBANKING_STATEMENT_SUCCESSFUL_COUNTER, metricTagValueGetter);
    }
  }
}