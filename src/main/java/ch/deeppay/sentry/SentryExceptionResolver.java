package ch.deeppay.sentry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.spring.logging.SleuthBaggageMDCConfiguration;
import io.sentry.Sentry;
import io.sentry.event.Event;
import io.sentry.event.EventBuilder;
import io.sentry.event.interfaces.ExceptionInterface;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

@Log4j2
class SentryExceptionResolver implements HandlerExceptionResolver, Ordered {

  @Override
  public ModelAndView resolveException(HttpServletRequest request,
                                       HttpServletResponse response,
                                       Object handler,
                                       Exception ex) {

    if (ex instanceof DeepPayProblemException) {
      DeepPayProblemException e = (DeepPayProblemException) ex;
      if (Objects.nonNull(e.getStatus()) && !HttpStatus.valueOf(e.getStatus().getStatusCode()).is5xxServerError()) {
        log.debug("Sentry message is ignored");
        return null;
      }
    }

    String sessionTraceId = MDC.get(SleuthBaggageMDCConfiguration.SESSION_TRACE_ID_MDC_NAME);
    if (Objects.isNull(sessionTraceId)) {
      log.warn("Session trace id is not set!");
      sessionTraceId = StringUtils.EMPTY;
    }

    EventBuilder eventBuilder = new EventBuilder().withMessage(ex.getMessage())
                                                  .withLevel(Event.Level.ERROR)
                                                  .withTag(SleuthBaggageMDCConfiguration.SESSION_TRACE_ID_MDC_NAME, sessionTraceId)
                                                  .withSentryInterface(new ExceptionInterface(ex));

    Sentry.capture(eventBuilder);

    // null = run other HandlerExceptionResolvers to actually handle the exception
    return null;
  }

  @Override
  public int getOrder() {
    // ensure this resolver runs first so that all exceptions are reported
    return Integer.MIN_VALUE;
  }
}