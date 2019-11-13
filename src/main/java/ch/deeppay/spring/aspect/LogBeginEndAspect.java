package ch.deeppay.spring.aspect;

import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Log4j2
@Aspect
@Component
public class LogBeginEndAspect {

  // <any return value> <package name> < any numbers of parameters>
  // since this aspect is used for services using this lib and there is no abacus package in this lib
  // this is marked as faulty.
  @Around("execution(* ch.abacus..*(..))")
  public Object logBeginEnd(ProceedingJoinPoint joinPoint) throws Throwable {
    if (log.isTraceEnabled()) {
      return createTraceInfo(joinPoint);
    } else if (log.isDebugEnabled()) {
      return createDebugInfo(joinPoint);
    } else {
      return joinPoint.proceed();
    }
  }

  private Object createDebugInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    Object retVal;

    try {
      StringBuilder startMessageStringBuffer = new StringBuilder();

      startMessageStringBuffer.append("Starting method ");
      startMessageStringBuffer.append(joinPoint.getSignature().getName());
      startMessageStringBuffer.append('(');

      Object[] args = joinPoint.getArgs();
      if (ArrayUtils.isNotEmpty(args)) {
        startMessageStringBuffer.append(StringUtils.join(args, ","));
      }
      startMessageStringBuffer.append(')');

      log.debug(startMessageStringBuffer.toString());
      retVal = joinPoint.proceed();

      String endMessageStringBuffer = "Finished method "
                                      + joinPoint.getSignature().getName()
                                      + "(..)";
      log.debug(endMessageStringBuffer);
    } catch (Throwable ex) {
      log.error(ex);
      throw ex;
    }
    return retVal;
  }

  private Object createTraceInfo(ProceedingJoinPoint joinPoint) throws Throwable {
    StopWatch stopWatch = new StopWatch();
    stopWatch.start();
    final Object retVal = createDebugInfo(joinPoint);
    stopWatch.stop();
    StringBuilder message = new StringBuilder();
    message.append("Execution time: ").append(stopWatch.getTotalTimeMillis()).append(" ms");
    log.trace(message);
    log.trace("Result: " + retVal);
    return retVal;
  }
}
