package ch.deeppay.util;

/**
 * Possible bank login procedures.
 *
 * https://wiki.abacus.ch/display/EPAY/API+Design
 */
public enum LoginState {
  CHANGE_PASSWORD,
  MOBILE_TAN,
  ONE_TIME_PASSWORD,
  OPTICAL_CHALLENGE,
  GRID_CARD,
  CHOOSE,
  SUCCESS,
  ERROR,
  NOT_SUPPORTED
}
