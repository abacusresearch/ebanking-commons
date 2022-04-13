package ch.deeppay.rest.async;

import ch.deeppay.rest.async.client.JobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean({AsyncConfiguration.class})
public class AsyncResponseSaveFactory {


  private final StorageService storageService;
  private final JobClient jobClient;
  private final String bucketName;

  public AsyncResponseSaveFactory(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient,
                                  @NonNull @Value("${spring.application.name}") final String applicationName) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.bucketName = AsyncConfiguration.getBucketName(applicationName);
  }

  public AsyncResponseSaveHandler create(final AsyncContextDataProvider contextDataProvider) {
    return new AsyncResponseSaveHandler(storageService, jobClient, bucketName, contextDataProvider.getSubjectClaim(), contextDataProvider.getFormat());
  }

}