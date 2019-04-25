package ch.deeppay.util;

public enum StatusCode {

  OK(0),
  ERROR(1),

  // general
  INTERFACE_ERROR(100),
  ACCOUNT_DISABLED(101),
  SESSION_EXPIRED(102),
  OPERATION_NOT_SUPPORTED(103),

  // login
  INVALID_STATE(200),
  PASSWORD_CHANGE_REQUIRED(201),
  INVALID_USERNAME_PASSWORD(202),

  // payment
  INVALID_FILE_FORMAT(301),
  FILENAME_TO_LONG(302),
  DUPLICATE(303),
  REJECT(304);

  private final int code;

  StatusCode(int code) {
    this.code = code;
  }

  public int getCode() {
    return code;
  }
}
