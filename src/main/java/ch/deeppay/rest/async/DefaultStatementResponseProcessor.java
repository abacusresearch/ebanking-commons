package ch.deeppay.rest.async;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.models.ebanking.statement.StatementResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Log4j2
public class DefaultStatementResponseProcessor implements ResponseProcessor<ResponseEntity<StatementResponse>> {

  private final StatementResponse statementResponse;
  private final StatementMetricCounter metricCounter;

  public DefaultStatementResponseProcessor(@Nonnull final StatementResponse statementResponse,@Nullable final StatementMetricCounter metricCounter) {
    this.statementResponse = statementResponse;
    this.metricCounter = metricCounter;
  }

  @Override
  public ResponseEntity<StatementResponse> process(final boolean isAsynchronous, final ResponseData responseData) {
    if (isAsynchronous) {
      statementResponse.setJobId(responseData.getIdentifier());
    } else {
      try {
        statementResponse.setFile(IOUtils.toString(responseData.getResponse(), StandardCharsets.UTF_8));
      } catch (IOException e) {
        log.error("IOException: ", e);
        throw DeepPayProblemException.createServerErrorProblemException(e.getMessage());
      }
    }

    ResponseEntity<StatementResponse> result;
    if(StringUtils.isNotEmpty(statementResponse.getJobId())){
      result = ResponseEntity.accepted().body(statementResponse);
    }else if(StringUtils.isEmpty(statementResponse.getFile())){
      result = new ResponseEntity<>(statementResponse, HttpStatus.NO_CONTENT);
    }else{
      result = new ResponseEntity<>(statementResponse, HttpStatus.OK);
    }

    if(Objects.nonNull(metricCounter)){
      metricCounter.count(result);
    }

    return result;

  }
}