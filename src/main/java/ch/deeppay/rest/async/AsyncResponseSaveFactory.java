package ch.deeppay.rest.async;

import ch.deeppay.rest.async.client.JobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AsyncResponseSaveFactory {


  private final StorageService storageService;
  private final JobClient jobClient;
  private final String serviceName;

  public AsyncResponseSaveFactory(@NonNull final StorageService storageService,
                                  @NonNull final JobClient jobClient,
                                  @NonNull @Value("${spring.application.name}") final String serviceName) {
    this.storageService = storageService;
    this.jobClient = jobClient;
    this.serviceName = serviceName;
  }

  public AsyncResponseSaveHandler create(final AsyncContextDataProvider contextDataProvider) {
    return new AsyncResponseSaveHandler(storageService, jobClient, serviceName, contextDataProvider.getSubjectClaim());
  }

}