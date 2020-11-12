package ch.deeppay.models.accounting.statement;

/**
 * Possible payment states
 */
public enum PaymentState {
  PENDING,
  COMPLETED,
  DECLINED,
  FAILED,
  REVERTED
}
