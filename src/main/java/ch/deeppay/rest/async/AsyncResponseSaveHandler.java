package ch.deeppay.rest.async;

import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;

import ch.deeppay.rest.async.client.JobClient;
import ch.deeppay.rest.async.client.JobRequest;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;

import static org.apache.commons.lang3.exception.ExceptionUtils.getMessage;

@Log4j2
public class AsyncResponseSaveHandler {

  private final StorageService storageService;
  private final JobClient jobClient;
  private final String serviceName;
  private final String subjectClaim;


  public AsyncResponseSaveHandler(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient,
                                  @NonNull final String serviceName,
                                  @NonNull final String subjectClaim) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.serviceName = serviceName;
    this.subjectClaim = subjectClaim;
  }


  public void saveResponse(final ResponseData responseData) {
    String jobId = responseData.getIdentifier();
    if (Objects.nonNull(responseData.getException())) {
      createJob(createBuilder(jobId)
                    .errorMessage(getMessage(responseData.getException()))
                    .build());
    } else {
      try {
        String path = createPath(jobId); //TODO check if subjectClaim is empty
        storageService.upload(path, responseData.getResponse().getBytes(StandardCharsets.UTF_8));
        if (!createJob(createBuilder(jobId)
                           .path(path)
                           .build())) {
          delete(path);
        }
      } catch (Exception e) {
        log.error("Error at JobId " + jobId, e);
        createJob(createBuilder(jobId)
                      .errorMessage(getMessage(e))
                      .build());
      }
    }
  }

  private String createPath(String jobId) {
    if (StringUtils.isEmpty(subjectClaim)) {
      log.error("SubjectClaim is empty");
      return jobId + "/" + UUID.randomUUID();
    } else {
      return subjectClaim + "/" + jobId + "/" + UUID.randomUUID();
    }
  }

  private JobRequest.JobRequestBuilder createBuilder(String jobId) {
    return JobRequest.builder()
                     .jobId(jobId)
                     .bucketName(serviceName)
                     .subjectClaim(subjectClaim);
  }

  private void delete(String path) {
    try {
      storageService.delete(path);
    } catch (Exception e) {
      log.error("Delete file failed: ", e);
    }
  }

  private boolean createJob(JobRequest jobRequest) {
    //TODO implement retry
    try {
      jobClient.createJob(jobRequest);
      return true;
    } catch (Exception e) {
      log.error(e);
    }
    return false;
  }

}