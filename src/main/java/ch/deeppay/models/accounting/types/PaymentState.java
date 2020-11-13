package ch.deeppay.models.accounting.types;

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
