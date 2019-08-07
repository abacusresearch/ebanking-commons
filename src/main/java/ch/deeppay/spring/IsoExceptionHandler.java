package ch.deeppay.spring;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.zalando.problem.spring.web.advice.ProblemHandling;

@SuppressWarnings("unused")
@ControllerAdvice
public class IsoExceptionHandler implements ProblemHandling {

}
