package ch.deeppay.models.ebanking.statement;

import ch.deeppay.util.FileFormat;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestStatementResponse {

  @Test
  public void test() throws JsonProcessingException {
    StatementResponse request = StatementResponse.builder()
        .format(new FileFormat("myName", "ext", true, false))
        .build();

    assertEquals("myName", request.getFormat().name());
    assertEquals("ext", request.getFormat().getFileExtension());
    assertFalse(request.getFormat().isUpload());
    assertTrue(request.getFormat().isDownload());
  }

}