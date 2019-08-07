package ch.deeppay.spring;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.problem.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

@SuppressWarnings("SpringFacetCodeInspection")
@Configuration
public class ErrorResponseConfiguration {

  @Bean
  public ObjectMapper objectMapper() {
    return new ObjectMapper().registerModules(
        new ProblemModule(),
        new ConstraintViolationProblemModule());
  }

}
