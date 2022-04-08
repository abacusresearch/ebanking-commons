package ch.deeppay.rest.async;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

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

  public DefaultStatementResponseProcessor(final StatementResponse statementResponse) {
    this.statementResponse = statementResponse;
  }

  @Override
  public ResponseEntity<StatementResponse> process(final Boolean isAsynchronous, final ResponseData responseData) {
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

    if(StringUtils.isNotEmpty(statementResponse.getJobId())){
      return ResponseEntity.accepted().body(statementResponse);
    }else if(StringUtils.isEmpty(statementResponse.getFile())){
      return new ResponseEntity<>(statementResponse, HttpStatus.NO_CONTENT);
    }else{
      return new ResponseEntity<>(statementResponse, HttpStatus.OK);
    }
  }
}