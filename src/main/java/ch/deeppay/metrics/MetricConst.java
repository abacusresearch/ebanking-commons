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
  String EBANKING_LOGIN_COUNTER = "ebanking.login.counter";
  String EBANKING_LOGIN_COUNTER_DESCRIPTION = "The number of login calls (before step one)";

  String EBANKING_LOGIN_SUCCESSFUL_COUNTER = "ebanking.login.successful.counter";
  String EBANKING_LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful logins";

  String EBANKING_PAYMENT_SUCCESSFUL_COUNTER = "ebanking.payment.successful.counter";
  String EBANKING_PAYMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful uploaded payments";

  String EBANKING_STATEMENT_SUCCESSFUL_COUNTER = "ebanking.statement.successful.counter";
  String EBANKING_STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful downloaded statements";

  String EBANKING_JOBS_SUCCESSFUL_COUNTER = "ebanking.jobs.successful.counter";
  String EBANKING_JOBS_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful downloaded jobs";

  String EBANKING_ASYNC_STATEMENT_SUCCESSFUL_COUNTER = "ebanking.statement.async.successful.counter";
  String EBANKING_ASYNC_STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful statements that has to be downloaded asynchronous";

  String EBANKING_EVENT_SUCCESSFUL_COUNTER = "ebanking.event.successful.counter";
  String EBANKING_EVENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful invoked (external) event requests";

  //
  //Definition for certificate metrics
  String CERT_EXPIRES_IN_SECONDS = "cert.expires.in.seconds";
  String CERT_EXPIRES_IN_SECONDS_DESCRIPTION = "Number of seconds til the cert expires.";

  String CERT_EXPIRES_AFTER_TIMESTAMP = "cert.expires.after.timestamp";
  String CERT_EXPIRES_AFTER_TIMESTAMP_DESCRIPTION = "Timestamp after the certificate expires.";

  String CERT_ERROR_COUNTER = "cert.error.counter";
  String CERT_ERROR_COUNTER_DESCRIPTION = "Total number of certificate load errors.";

  //
  //Definition for accounting metrics
  String ACCOUNTING_LOGIN_COUNTER = "accounting.login.counter";
  String ACCOUNTING_LOGIN_COUNTER_DESCRIPTION = "The number of login calls";

  String ACCOUNTING_LOGIN_SUCCESSFUL_COUNTER = "accounting.login.successful.counter";
  String ACCOUNTING_LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful logins";

  String ACCOUNTING_STATEMENT_SUCCESSFUL_COUNTER = "accounting.statement.successful.counter";
  String ACCOUNTING_STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful downloaded statements";


}