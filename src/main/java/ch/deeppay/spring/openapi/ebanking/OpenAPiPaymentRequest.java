package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.util.FileFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.lang.Nullable;

import static ch.deeppay.spring.openapi.ebanking.OpenApiTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name = "SimplePaymentRequest", description = "Request to upload a payment file")
public interface OpenAPiPaymentRequest {

  @Schema(description = "Format type of the file", example = "PAIN001", implementation = FileFormat.class)
  FileFormat getFormat();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION, required = true)
  String getTransportData();

  @Nullable
  @Schema(description = "Payment file that has to be uploaded.")
  default String getFile(){return "";}

}
