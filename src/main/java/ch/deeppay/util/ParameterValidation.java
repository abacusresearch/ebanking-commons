package ch.deeppay.util;

import ch.deeppay.models.TransportData;
import ch.deeppay.models.login.LoginRequest;
import ch.deeppay.models.payment.PaymentRequest;
import ch.deeppay.models.statement.StatementRequestDto;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

public class ParameterValidation {

  private static int MAX_LENGTH_FILE_NAME = 40;

  public static void verifyLoginParameter(@Nullable final HttpHeaders headers, @NonNull final LoginRequest clientRequest) {
    List<String> missingParameters = new ArrayList<>();

    if (clientRequest.getChallenge().isEmpty() && clientRequest.getPasswordNew().isEmpty()) {
      missingParameters.addAll(verifyLoginStep1(clientRequest));
    }

    if (!clientRequest.getChallenge().isEmpty() || !clientRequest.getPasswordNew().isEmpty()) {
      missingParameters.addAll(verifyLoginStep2(headers, clientRequest));
    }

    if (!missingParameters.isEmpty()) {
      throw new ParameterException("Missing Parameters: " + String.join(",", missingParameters));
    }
  }

  // login step 2 or password
  private static List<String> verifyLoginStep2(@Nullable HttpHeaders headers, @NonNull LoginRequest clientRequest) {
    List<String> missingParameters = new ArrayList<>();
    if (clientRequest.getTransportData().isEmpty()) {
      missingParameters.add("transportData");
    }

    if (headers == null || CookieHelper.getClientCookies(headers).length == 0) {
      missingParameters.add("cookie");
    }
    return missingParameters;
  }

  private static List<String> verifyLoginStep1(@NonNull LoginRequest clientRequest) {
    List<String> missingParameters = new ArrayList<>();
    // login step 1
    if (clientRequest.getLanguage().isEmpty()) {
      missingParameters.add("language");
    }
    if (clientRequest.getBankId().isEmpty()) {
      missingParameters.add("bankId");
    }
    if (clientRequest.getContractId().isEmpty()) {
      missingParameters.add("contractId");
    }
    if (clientRequest.getPassword().isEmpty()) {
      missingParameters.add("password");
    }
    return missingParameters;
  }

  public static void verifyStatementsParameter(@NonNull final HttpHeaders headers, @NonNull final StatementRequestDto statementRequest) {
    List<String> missingParameters = new ArrayList<>(verifyRequiredParams(headers, statementRequest.getTransportData()));

    if (statementRequest.getTransportData().getInterfaceUrl() == null || statementRequest.getTransportData().getInterfaceUrl().isEmpty()) {
      missingParameters.add("interfaceUrl in transportData");
    }

    if (!missingParameters.isEmpty()) {
      throw new ParameterException("Missing Parameters: " + String.join(",", missingParameters));
    }
  }

  public static void verifyLoginStepTwoParameter(@NonNull final HttpHeaders headers, @NonNull final LoginRequest clientRequest) {
    TransportData transportData = TransportData.getTransportData(clientRequest.getTransportData());
    List<String> missingParameters = new ArrayList<>(verifyRequiredParams(headers, transportData));

    if (clientRequest.getChallenge().isEmpty()) {
      missingParameters.add("challenge");
    }

    if (!missingParameters.isEmpty()) {
      throw new ParameterException("Missing Parameters: " + String.join(",", missingParameters));
    }
  }

  private static List<String> verifyRequiredParams(@NonNull HttpHeaders headers, TransportData transportData) {
    List<String> missingParameters = new ArrayList<>();
    if (transportData.getBankId().isEmpty()) {
      missingParameters.add("bankId");
    }

    if (CookieHelper.getClientCookies(headers).length == 0) {
      missingParameters.add("cookie");
    }
    return missingParameters;
  }

  public static void verifyPaymentParameter(@NonNull final HttpHeaders headers, @NonNull final PaymentRequest clientRequest,
                                            @NonNull final MultipartFile file) {
    TransportData transportData = TransportData.getTransportData(clientRequest.getTransportData());
    List<String> missingParameters = new ArrayList<>(verifyRequiredParams(headers, transportData));

    if (!missingParameters.isEmpty()) {
      throw new ParameterException("Missing Parameters: " + String.join(",", missingParameters));
    }

    if (file.getOriginalFilename() != null && file.getOriginalFilename().length() > MAX_LENGTH_FILE_NAME) {
      throw new UserException("File names must not be longer than 40 characters.");
    }
  }
}
