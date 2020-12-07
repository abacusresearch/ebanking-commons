package ch.deeppay.util;

/**
 * Possible bank login procedures.
 *
 * https://wiki.deeppay.swiss/bin/view/Entwicklung/Architektur/API%20Design/
 */
public enum LoginState {
  CHANGE_PASSWORD,
  MOBILE_TAN,
  PUSH_TAN,
  ONE_TIME_PASSWORD,
  OPTICAL_CHALLENGE,
  GRID_CARD,
  KEYFILE,
  CHOOSE,
  SUCCESS,
  ERROR,
  NOT_SUPPORTED
}
