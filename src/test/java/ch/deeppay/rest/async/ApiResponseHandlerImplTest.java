package ch.deeppay.rest.async;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import ch.deeppay.models.accounting.StatementResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class ApiResponseHandlerImplTest {

  @Mock
  private AsyncResponseSaveFactory asyncResponseSaveFactory;

  @Mock
  private AsyncContextDataProvider contextDataProvider;

  @Mock
  private AsyncResponseSaveHandlerImpl asyncResponseSaveHandler;

  ApiResponseHandlerImpl<StatementResponse> testee;

  @BeforeEach
  private void before(){
    MockitoAnnotations.openMocks(this);
    Mockito.doReturn(asyncResponseSaveHandler).when(asyncResponseSaveFactory).create(Mockito.any());
    Mockito.doReturn("1234").when(asyncResponseSaveHandler).getIdentifier();
    testee = new ApiResponseHandlerImpl<>(asyncResponseSaveFactory, contextDataProvider);
  }

  @Test
  void testProcessResponse(){
    StatementResponse result = testee.processResponse((isAsynchronous, responseData) -> {
      Assertions.assertFalse(isAsynchronous);
      Assertions.assertNull(responseData.getIdentifier());
      return new StatementResponse();
    });
    Assertions.assertNotNull(result);
    Mockito.verify(asyncResponseSaveFactory,Mockito.never()).create(Mockito.any());
  }



  @Test
  void testProcessResponseWithSetAsyncFlagBefore(){
    testee.setResponseAsync(true);
    StatementResponse result = testee.processResponse((isAsynchronous, responseData) -> {
      Assertions.assertTrue(isAsynchronous);
      Assertions.assertEquals("1234",responseData.getIdentifier());
      return new StatementResponse();
    });
    Assertions.assertNotNull(result);
    Mockito.verify(asyncResponseSaveFactory,Mockito.times(1)).create(Mockito.any());
  }


  @Test
  void testProcessResponseWithResetAsyncFlag(){
    testee.setResponseAsync(true);
    testee.applyResponse(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8)), null);
    StatementResponse result = testee.processResponse((isAsynchronous, responseData) -> {
      Assertions.assertFalse(isAsynchronous);
      Assertions.assertNull(responseData.getIdentifier());
      return new StatementResponse();
    });
    Assertions.assertNotNull(result);
    Mockito.verify(asyncResponseSaveFactory,Mockito.never()).create(Mockito.any());
  }



  @Test
  void testApplyResponseAsync(){
    testee.setResponseAsync(true);

    StatementResponse result = testee.processResponse((isAsynchronous, responseData) -> {
      Assertions.assertTrue(isAsynchronous);
      Assertions.assertEquals("1234",responseData.getIdentifier());
      return new StatementResponse();
    });
    Assertions.assertNotNull(result);

    testee.applyResponse(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8)), null);

    Mockito.verify(asyncResponseSaveFactory,Mockito.times(1)).create(Mockito.any());
    Mockito.verify(asyncResponseSaveHandler,Mockito.times(1)).handleResponse((InputStream) Mockito.any());
    Mockito.verify(asyncResponseSaveHandler,Mockito.never()).handleResponse((Exception) Mockito.any());
  }

}