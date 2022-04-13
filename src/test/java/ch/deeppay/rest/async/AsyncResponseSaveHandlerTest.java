package ch.deeppay.rest.async;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import ch.deeppay.rest.async.client.JobClient;
import ch.deeppay.rest.async.client.JobRequest;
import ch.deeppay.util.FileFormat;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

class AsyncResponseSaveHandlerTest {


  @Mock
  private StorageService storageService;

  @Mock
  private JobClient jobClient;

  private AsyncResponseSaveHandler testee;

  @BeforeEach
  private void before() {
    MockitoAnnotations.openMocks(this);
    testee = new AsyncResponseSaveHandler(storageService, jobClient, "SERVICE_NAME", "SUBJECT_CLAIM", FileFormat.CAMT053);
  }

  @Test
  void testHandleResponseWithFileUpload() throws Exception {
    Mockito.doReturn("SALT").when(storageService).upload(Mockito.anyString(), Mockito.any());
    Mockito.doAnswer(invocation -> {
      JobRequest request = invocation.getArgument(0);
      Assertions.assertNull(request.getErrorMessage());
      Assertions.assertEquals(testee.getIdentifier(), request.getJobId());
      Assertions.assertEquals("SERVICE_NAME", request.getBucketName());
      Assertions.assertEquals("SUBJECT_CLAIM", request.getSubjectClaim());
      Assertions.assertEquals(FileFormat.CAMT053.name(), request.getFormat());
      Assertions.assertTrue(StringUtils.startsWith(request.getObjectPath(), "SUBJECT_CLAIM/" + testee.getIdentifier() + "/"));
      return null;
    }).when(jobClient).createOrUpdateJob(Mockito.any());

    testee.handleResponse(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8)));
    Mockito.verify(jobClient, Mockito.times(1)).createOrUpdateJob(Mockito.any());
    Mockito.verify(storageService, Mockito.never()).delete(Mockito.any());
  }

  @Test
  void testHandleResponseWithFailedFileUpload() throws Exception {
    Mockito.doThrow(new Exception("EXCEPTION")).when(storageService).upload(Mockito.anyString(), Mockito.any());
    Mockito.doAnswer(invocation -> {
      JobRequest request = invocation.getArgument(0);
      Assertions.assertEquals("EXCEPTION", request.getErrorMessage());
      Assertions.assertEquals(testee.getIdentifier(), request.getJobId());
      Assertions.assertEquals("SERVICE_NAME", request.getBucketName());
      Assertions.assertEquals("SUBJECT_CLAIM", request.getSubjectClaim());
      Assertions.assertEquals(FileFormat.CAMT053.name(), request.getFormat());
      return null;
    }).when(jobClient).createOrUpdateJob(Mockito.any());

    testee.handleResponse(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8)));
  }

  @Test
  void testHandleResponseWithFailedUpdateJob() throws Exception {
    Mockito.doReturn("SALT").when(storageService).upload(Mockito.anyString(), Mockito.any());
    Mockito.doThrow(new RuntimeException("EXCEPTION")).when(jobClient).createOrUpdateJob(Mockito.any());

    testee.handleResponse(new ByteArrayInputStream("TEST".getBytes(StandardCharsets.UTF_8)));
    Mockito.verify(jobClient, Mockito.times(1)).createOrUpdateJob(Mockito.any());
    Mockito.verify(storageService, Mockito.times(1)).delete(Mockito.any());
  }

}