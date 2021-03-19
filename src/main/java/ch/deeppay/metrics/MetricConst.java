package ch.deeppay.metrics;

public interface MetricConst {

  String TYPE = "type";
  String BANK_ID = "bankId";
  String CUSTOMER = "customer";
  String CLIENT_TYPE = "clientType";
  String CLIENT_ID = "clientId";
  String FORMAT = "format";

  String LOGIN_COUNTER = "ebanking.login.counter";
  String LOGIN_COUNTER_DESCRIPTION = "The number of login calls (before step one)";

  String LOGIN_SUCCESSFUL_COUNTER = "ebanking.login.successful.counter";
  String LOGIN_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful logins";

  String PAYMENT_SUCCESSFUL_COUNTER = "ebanking.payment.successful.counter";
  String PAYMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful uploaded payments";

  String STATEMENT_SUCCESSFUL_COUNTER = "ebanking.statement.successful.counter";
  String STATEMENT_SUCCESSFUL_COUNTER_DESCRIPTION = "The number of successful downloaded statements";

}