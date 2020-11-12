package ch.deeppay.models.accounting.statement;

import org.springframework.lang.Nullable;

import javax.annotation.Nonnull;

/**
 * Brands of credit cards.
 */
public enum CardBrand {
  VISA("4"),
  MASTERCARD("5"),
  DISCOVER("6"),
  AMERICAN_EXPRESS("37");

  private final String startsWith;

  CardBrand(@Nonnull final String startsWith) {
    this.startsWith = startsWith;
  }

  @Nullable
  public static String findBrand(@Nullable final String cardNumber) {
    if (cardNumber != null) {
      if (cardNumber.startsWith(VISA.getStartsWith())) {
        return VISA.name();
      }
      if (cardNumber.startsWith(MASTERCARD.getStartsWith())) {
        return MASTERCARD.name();
      }
      if (cardNumber.startsWith(DISCOVER.getStartsWith())) {
        return DISCOVER.name();
      }
      if (cardNumber.startsWith(AMERICAN_EXPRESS.getStartsWith())) {
        return AMERICAN_EXPRESS.name();
      }
    }
    return null;
  }

  public String getStartsWith() {
    return startsWith;
  }
}
