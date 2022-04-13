package ch.deeppay.rest.async;

import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.rest.async.client.JobClient;
import ch.deeppay.rest.async.client.JobRequest;
import ch.deeppay.util.FileFormat;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.StringUtils;

@Log4j2
public class AsyncResponseSaveHandler {

  private final StorageService storageService;
  private final JobClient jobClient;
  private final String bucketName;
  private final String subjectClaim;
  private final String identifier;
  private final FileFormat format;


  public AsyncResponseSaveHandler(final StorageService storageService,
                                  final JobClient jobClient,
                                  final String bucketName,
                                  final String subjectClaim,
                                  final FileFormat format) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.bucketName = bucketName;
    this.subjectClaim = subjectClaim;
    this.format = format;
    identifier = UUID.randomUUID().toString();
  }


  public void handleResponse(final InputStream stream) {
    if (StringUtils.isEmpty(subjectClaim)) {
      throw new IllegalArgumentException("Subject claim can not be empty");
    }

    try {
      String path = createPath(identifier);
      String salt = storageService.upload(path, stream);
      if (!createOrUpdateJob(createBuilder(identifier)
                                 .objectPath(path)
                                 .salt(salt)
                                 .build())) {
        delete(path);
      }
    } catch (Exception e) {
      log.error("Error at JobId " + identifier, e);
      createOrUpdateJob(createBuilder(identifier)
                            .errorMessage(getMessage(e))
                            .build());
    }

  }

  public void handleResponse(final Throwable exception) {
    createOrUpdateJob(createBuilder(identifier)
                          .errorMessage(getMessage(exception))
                          .build()); //Ignore failed job because nothing can be done.

  }


  public void createJob() {
    if(!createOrUpdateJob(createBuilder(identifier)
                          .build())){
      throw DeepPayProblemException.createServerErrorProblemException("Failed to create a async job");
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
                     .bucketName(bucketName)
                     .subjectClaim(subjectClaim)
                     .format(format.name());
  }

  private void delete(String path) {
    try {
      storageService.delete(path);
    } catch (Exception e) {
      log.error("Delete file failed: ", e);
    }
  }

  private boolean createOrUpdateJob(JobRequest jobRequest) {
    try {
      jobClient.createOrUpdateJob(jobRequest);
      return true;
    } catch (Exception e) {
      log.error(e);
    }
    return false;
  }

  public String getIdentifier() {
    return identifier;
  }

  public String getMessage(final Throwable th) {
    return Objects.isNull(th) || Objects.isNull(th.getMessage()) ? StringUtils.EMPTY : StringUtils.defaultString(th.getMessage());
  }

}