package ch.deeppay.rest.async;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import ch.deeppay.models.ebanking.statement.StatementResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultStatementResponseProcessorTest {

  private StatementResponse statementResponse;

  private DefaultStatementResponseProcessor testee;

  @BeforeEach
  void before() {
    statementResponse = new StatementResponse();
    testee = new DefaultStatementResponseProcessor(statementResponse);
  }

  @Test
  void testProcessAsync() {
    testee.process(true, ResponseData.builder().response(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8))).identifier("1234").build());
    Assertions.assertNull(statementResponse.getFile());
    Assertions.assertEquals("1234", statementResponse.getJobId());
  }

  @Test
  void testProcess() {
    testee.process(false, ResponseData.builder().response(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8))).build());
    Assertions.assertEquals("TEST", statementResponse.getFile());
  }

}