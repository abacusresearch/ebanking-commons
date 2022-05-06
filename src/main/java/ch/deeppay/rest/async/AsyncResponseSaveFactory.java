package ch.deeppay.rest.async;

import ch.deeppay.rest.async.client.JobClient;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean({AsyncConfiguration.class})
public class AsyncResponseSaveFactory {


  private final StorageService storageService;
  private final JobClient jobClient;

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${ch.deeppay.job.minio.bucketName:}")
  private String bucketName;

  public AsyncResponseSaveFactory(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient) {
    this.storageService = storageService;
    this.jobClient = jobClient;
  }

  public AsyncResponseSaveHandler create(final AsyncContextDataProvider contextDataProvider) {
    return new AsyncResponseSaveHandler(storageService,
                                        jobClient,
                                        StringUtils.isEmpty(bucketName) ? AsyncConfiguration.getBucketName(applicationName) : bucketName,
                                        contextDataProvider.getSubjectClaim(),
                                        contextDataProvider.getFormat());
  }

}