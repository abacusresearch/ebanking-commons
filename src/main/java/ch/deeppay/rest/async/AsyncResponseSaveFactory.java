package ch.deeppay.rest.async;

import ch.deeppay.rest.async.client.JobClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean({AsyncConfiguration.class})
public class AsyncResponseSaveFactory {

  private final StorageService storageService;
  private final JobClient jobClient;
  private final AsyncConfiguration asyncConfiguration;

  public AsyncResponseSaveFactory(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient,
                                  @NonNull final AsyncConfiguration asyncConfiguration) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.asyncConfiguration = asyncConfiguration;
  }

  public AsyncResponseSaveHandler create(final AsyncContextDataProvider contextDataProvider) {
    return new AsyncResponseSaveHandler(storageService,
                                        jobClient,
                                        asyncConfiguration.getMinioBucketName(),
                                        contextDataProvider.getSubjectClaim(),
                                        contextDataProvider.getFormat());
  }

}