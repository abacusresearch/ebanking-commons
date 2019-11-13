package ch.deeppay.exception;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    Problem problem = entity.getBody();
    if (problem != null) {

      //defaults
      String detail = problem.getDetail();

      if (problem instanceof ConstraintViolationProblem) {
        detail = getContrainViolationDetails((ConstraintViolationProblem) problem);
      }

      Problem traceIdProblem = Problem.builder()
          .withTitle(problem.getTitle())
          .withDetail(detail)
          .withStatus(problem.getStatus())
          .withInstance(problem.getInstance())
          .with("id", getTraceId())
          .build();

      return new ResponseEntity<>(traceIdProblem, entity.getStatusCode());
    }
    return entity;
  }

  /**
   * List details in reponse details.
   *
   * @param body b
   * @return details as list of violations.
   */
  private String getContrainViolationDetails(ConstraintViolationProblem problem) {
    String detail;
    final List<Violation> violations = problem.getViolations();
    List<String> details = new ArrayList<>();
    violations.forEach(violation -> details.add(violation.getField() + ": " + violation.getMessage()));
    detail = StringUtils.join(details, "\n");
    return detail;
  }

  @NonNull
  private String getTraceId() {
    return Optional.ofNullable(tracer)
        .map(Tracer::currentSpan)
        .map(Span::context)
        .map(TraceContext::traceIdString)
        .orElse(StringUtils.EMPTY);
  }
}
