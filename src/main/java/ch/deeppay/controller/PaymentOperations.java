package ch.deeppay.controller;

import ch.deeppay.models.payment.PaymentRequest;
import ch.deeppay.models.payment.PaymentResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * Rest endpoints for uploading payment files.
 */
@RestController
@RequestMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PaymentOperations {

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<PaymentResponse> uploadPayment(@RequestHeader @NonNull final HttpHeaders httpHeaders,
                                                @RequestParam("file") final @NotNull(message = "Upload file cannot be empty.") MultipartFile file,
                                                final @Valid PaymentRequest paymentRequest);
}
