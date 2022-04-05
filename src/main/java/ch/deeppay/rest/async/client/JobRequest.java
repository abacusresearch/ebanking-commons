package ch.deeppay.rest.async.client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobRequest {
  private String jobId;
  private String bucketName;
  private String path;
  private String subjectClaim;
  private String errorMessage;

}