package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.util.FileFormat;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "JobRequest", description = "Request to create or update a job")
public interface OpenAPiJobRequest {

  @Schema(description = "Unique identifier of a job")
  String getJobId();

  @Schema(description = "Name of the service (normally spring.application.name from the application.yml is used", example = "finnova")
  String getServiceName();

  @Schema(description = "path under which the file was saved on minio")
  String getObjectPath();

  @Schema(description = "Subject claim of a jwt token. Used to identify which client has access")
  String getSubjectClaim();

  @Schema(description = "Contains an error message when an error when file upload was not successful")
  String getErrorMessage();

  @Schema(description = "Format type of the file", example = "PAIN001", implementation = FileFormat.class)
  String getFormat();

  @Schema(description = "Salt that is used to encrypt the file on minio.")
  String getSalt();

}
