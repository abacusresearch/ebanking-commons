package ch.deeppay.rest.async;

import ch.deeppay.models.ebanking.statement.StatementResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


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
      statementResponse.setFile(responseData.getResponse());
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