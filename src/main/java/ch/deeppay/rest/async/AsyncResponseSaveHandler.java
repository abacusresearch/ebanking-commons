package ch.deeppay.rest.async;

import java.io.InputStream;
import java.util.UUID;

import ch.deeppay.rest.async.client.JobClient;
import ch.deeppay.rest.async.client.JobRequest;
import ch.deeppay.util.FileFormat;
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
  private final String identifier;
  private final FileFormat format;


  public AsyncResponseSaveHandler(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient,
                                  @NonNull final String serviceName,
                                  @NonNull final String subjectClaim,
                                  @NonNull final FileFormat format) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.serviceName = serviceName;
    this.subjectClaim = subjectClaim;
    this.format = format;
    identifier = UUID.randomUUID().toString();
  }


  public void handleResponse(final InputStream stream) {
      try {
        String path = createPath(identifier); //TODO check if subjectClaim is empty
        storageService.upload(path, stream);
        if (!createJob(createBuilder(identifier)
                           .objectPath(path)
                           .build())) {
          delete(path);
        }
      } catch (Exception e) {
        log.error("Error at JobId " + identifier, e);
        createJob(createBuilder(identifier)
                      .errorMessage(getMessage(e))
                      .build());
      }

  }

  public void handleResponse(final Throwable exception) {
      createJob(createBuilder(identifier)
                    .errorMessage(getMessage(exception))
                    .build());

  }


  public void createJob(){
    createJob(createBuilder(identifier)
                  .build());
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
                     .serviceName(serviceName)
                     .subjectClaim(subjectClaim)
                     .format(format);
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

  public String getIdentifier() {
    return identifier;
  }
}