package ch.deeppay.controller;

import ch.deeppay.models.ebanking.payment.PaymentRequest;
import ch.deeppay.models.ebanking.payment.PaymentResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

/**
 * Rest endpoints for uploading payment files.
 */
@RestController
@RequestMapping(value = "/payments", produces = MediaType.APPLICATION_JSON_VALUE)
public interface PaymentOperations {

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<PaymentResponse> uploadPayment(@RequestHeader @NonNull final HttpHeaders httpHeaders,
                                                @RequestParam("file") @Nullable final MultipartFile file,
                                                final @Valid PaymentRequest paymentRequest);
}
