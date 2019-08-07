package ch.deeppay.spring;

import brave.Tracer;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;

/**
 * Load traceId from sleuth library and add it to all problems
 */
@SuppressWarnings("unused")
@ControllerAdvice
public class ProblemExceptionHandler implements ProblemHandling {

  private final Tracer tracer;

  @Autowired
  public ProblemExceptionHandler(Tracer tracer) {
    this.tracer = tracer;
  }

  @Override
  public ResponseEntity<Problem> process(ResponseEntity<Problem> entity) {
    Problem body = entity.getBody();
    if (body != null) {
      Problem traceId = Problem.builder()
          .withTitle(body.getTitle())
          .withDetail(body.getDetail())
          .withStatus(body.getStatus())
          .withInstance(body.getInstance())
          .with("id", tracer != null ? tracer.currentSpan().context().traceIdString() : StringUtils.EMPTY)
          .build();

      return new ResponseEntity<>(traceId, entity.getStatusCode());
    }
    return entity;
  }
}
