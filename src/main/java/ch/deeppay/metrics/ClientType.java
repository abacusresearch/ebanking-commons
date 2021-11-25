package ch.deeppay.metrics;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

/**
 * Check OpenAPI documentation definition at ch.deeppay.spring.openapi.* when values are updated
 */
public enum ClientType {

  ABACUS_G4(UserAgentConst.ABACUS_G4),
  ABANINJA(UserAgentConst.ABANINJA),
  POSTMAN(UserAgentConst.POSTMAN),
  SWISS21(UserAgentConst.SWISS21),
  DEEPBOX(UserAgentConst.DEEPBOX),
  ABASALARY(UserAgentConst.ABASALARY),
  UNKNOWN("");

  private final String userAgent;

  ClientType(final String userAgent) {
    this.userAgent = userAgent;
  }

  public String getUserAgent() {
    return userAgent;
  }

  @Override
  public String toString() {
    return name().toLowerCase();
  }

  @Nullable
  public static ClientType fromUserAgent(String userAgent){
    for(ClientType type : ClientType.values()){
      if(StringUtils.startsWithIgnoreCase(userAgent, type.getUserAgent())){
        return type;
      }
    }
    return null;
  }

}