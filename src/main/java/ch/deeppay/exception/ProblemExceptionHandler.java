package ch.deeppay.exception;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import brave.Span;
import brave.Tracer;
import brave.baggage.BaggageField;
import brave.propagation.TraceContext;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.Problem;
import org.zalando.problem.spring.web.advice.ProblemHandling;
import org.zalando.problem.violations.ConstraintViolationProblem;
import org.zalando.problem.violations.Violation;

import static org.springframework.http.ResponseEntity.status;
import static org.zalando.problem.Problem.builder;

/**
 * Load traceId from sleuth library and add it to all problems
 */
@SuppressWarnings("unused")
@ControllerAdvice
public class ProblemExceptionHandler implements ProblemHandling {

  public final static String FIELD_TRACE_ID = "traceId";
  public final static String FIELD_SESSION_TRACE_ID = "sessionTraceId";

  private final Optional<Tracer> optionalTracer;
  private final HttpServletRequest request;
  private final Optional<BaggageField> optionalBaggageField;

  @Autowired
  public ProblemExceptionHandler(@Nullable final Tracer tracer,@NonNull final HttpServletRequest request,@Nullable final BaggageField sessionTraceId) {
    this.optionalTracer = Optional.ofNullable(tracer);
    this.request = request;
    this.optionalBaggageField = Optional.ofNullable(sessionTraceId);
  }

  @Override
  public ResponseEntity<Problem> process(ResponseEntity<Problem> entity) {
    Problem problem = entity.getBody();
    if (Objects.nonNull(problem)) {

      //defaults
      String detail = problem.getDetail();

      if (problem instanceof ConstraintViolationProblem) {
        detail = getConstraintViolationDetails((ConstraintViolationProblem) problem);
      }

      Problem extendedProblem = builder()
          .withType(problem.getType())
          .withTitle(problem.getTitle())
          .withDetail(detail)
          .withStatus(problem.getStatus())
          .withInstance(Objects.isNull(problem.getInstance()) ? getInstance() : problem.getInstance())
          .with(FIELD_TRACE_ID, getTraceId())
          .with(FIELD_SESSION_TRACE_ID, getSessionTraceId())
          .build();

      return status(entity.getStatusCode())
          .contentType(MediaType.APPLICATION_PROBLEM_JSON)
          .body(extendedProblem);
    }
    return entity;
  }

  private URI getInstance() {
    return Objects.isNull(request) ? null : URI.create(request.getRequestURI());
  }

  /**
   * List details in reponse details.
   *
   * @param problem b
   * @return details as list of violations.
   */
  private String getConstraintViolationDetails(ConstraintViolationProblem problem) {
    String detail;
    final List<Violation> violations = problem.getViolations();
    List<String> details = new ArrayList<>();
    violations.forEach(violation -> details.add(violation.getField() + ": " + violation.getMessage()));
    detail = StringUtils.join(details, "\n");
    return detail;
  }

  @NonNull
  private String getTraceId() {
    return optionalTracer.map(Tracer::currentSpan)
                         .map(Span::context)
                         .map(TraceContext::traceIdString)
                         .orElse(StringUtils.EMPTY);
  }

  @NonNull
  private  String getSessionTraceId(){
    return optionalBaggageField.map(BaggageField::getValue)
                               .orElse(StringUtils.EMPTY);
  }
}
