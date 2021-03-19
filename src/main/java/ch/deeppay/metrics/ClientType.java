package ch.deeppay.metrics;

public enum ClientType {
  ABACUS_G4,
  ABANINJA,
  POSTMAN,
  SWISS21,
  DEEPBOX,
  UNKNOWN;

  @Override
  public String toString() {
    return name().toLowerCase();
  }
}