package ch.deeppay.metrics;

public interface MetricConst {

  String TAG_TYPE = "type";
  String TAG_BANK_ID = "bankId";
  String TAG_CUSTOMER = "customer";
  String TAG_CLIENT_TYPE = "clientType";
  String TAG_CLIENT_ID = "clientId";
  String TAG_FORMAT = "format";
  String TAG_SUBJECT_NAME = "subjectName";
  String TAG_SUBJECT_ORGANIZATION = "subjectOrganization";

  //
  //Definition for ebanking metrics
  String LOGIN_COUNTER = "ebanking.login.counter";
  String LOGIN_COUNTER_DESCRIPTION = "The number of login calls (before step one)";

  String LOGIN_SUCCESSFUL_COUNTER = "ebanking.login.successful.counter";
  String LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful logins";

  String PAYMENT_SUCCESSFUL_COUNTER = "ebanking.payment.successful.counter";
  String PAYMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful uploaded payments";

  String STATEMENT_SUCCESSFUL_COUNTER = "ebanking.statement.successful.counter";
  String STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful downloaded statements";

  //
  //Definition for certificate metrics
  String CERT_EXPIRES_IN_SECONDS = "cert.expires.in.seconds";
  String CERT_EXPIRES_IN_SECONDS_DESCRIPTION = "Number of seconds til the cert expires.";

  String CERT_EXPIRES_AFTER_TIMESTAMP = "cert.expires.after.timestamp";
  String CERT_EXPIRES_AFTER_TIMESTAMP_DESCRIPTION = "Timestamp after the certificate expires.";

  String CERT_ERROR_COUNTER = "cert.error.counter";
  String CERT_ERROR_COUNTER_DESCRIPTION = "Total number of certificate load errors.";


}