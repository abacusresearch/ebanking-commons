package ch.deeppay.spring.openapi.ebanking;

import ch.deeppay.util.LoginState;
import io.swagger.v3.oas.annotations.media.Schema;

import static ch.deeppay.spring.openapi.ebanking.OpenApiBankingTextConst.SCHEMA_TRANSPORT_DATA_DESCRIPTION;

@Schema(name="PaymentResponseSimple")
public interface OpenApiPaymentResponseSimple {

  @Schema(description = "The variable contains a zipped and base64 encoded file. If a pain001 was uploaded it contains in the most cases a pain002 file.",required = true)
  String getFile();

  @Schema(description = "State of the response.", example = "SUCCESS",required = true)
  LoginState getState();

  @Schema(description = "Payment reference of the uploaded payment. The reference can be used to get updated information about the payment status", example = "1asfl254rtzvaslsd634f")
  String getPaymentReference();

  @Schema(description = SCHEMA_TRANSPORT_DATA_DESCRIPTION,required = true)
  String getTransportData();

}