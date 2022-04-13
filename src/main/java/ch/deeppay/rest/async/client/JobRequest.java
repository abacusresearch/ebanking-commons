package ch.deeppay.rest.async.client;

import ch.deeppay.spring.openapi.ebanking.OpenAPiJobRequest;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobRequest implements OpenAPiJobRequest {
  private String jobId;
  private String bucketName;
  private String objectPath;
  private String subjectClaim;
  private String errorMessage;
  private String format;
  private String salt;

}