package ch.deeppay.metrics;

import javax.annotation.Nullable;

import org.apache.commons.lang.StringUtils;

public enum ClientType {
  ABACUS_G4,
  ABANINJA,
  POSTMAN,
  SWISS21,
  DEEPBOX,
  ABASALARY,
  UNKNOWN;

  @Override
  public String toString() {
    return name().toLowerCase();
  }

  @Nullable
  public static ClientType get(String userAgent){
    for(ClientType type : ClientType.values()){
      if(StringUtils.startsWithIgnoreCase(userAgent, type.name())){
        return type;
      }
    }
    return null;
  }

}