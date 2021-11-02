package ch.deeppay.metrics;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

public enum ClientType {
  ABACUS_G4("Abacus-G4"),
  ABANINJA("AbaNinja"),
  POSTMAN("Postman"),
  SWISS21("Swiss21"),
  DEEPBOX("DeepBox"),
  ABASALARY("AbaSalary"),
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