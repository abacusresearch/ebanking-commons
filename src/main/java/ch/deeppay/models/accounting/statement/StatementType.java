package ch.deeppay.models.accounting.statement;

/**
 * Possible statement types
 */
public enum StatementType {

  CARD_PAYMENT,
  CARD_REFUND,
  CARD_CHARGEBACK,
  CARD_CREDIT,
  EXCHANGE,
  TRANSFER,
  FEE,
  REFUND,
  TOPUP,
  TOPUP_RETURN,
  TAX,
  TAX_REFUND,
  ATM,
  DEPOSIT
}
