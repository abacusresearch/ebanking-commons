package ch.deeppay.util;

import javax.annotation.Nullable;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;

public class Base64Util {

  private static final String BASE64_REG_EX = "^([A-Za-z0-9+/]{4})*([A-Za-z0-9+/]{3}=|[A-Za-z0-9+/]{2}==)?$";

  public static boolean isBase64(@Nullable final String str){
    if(StringUtils.isBlank(str)){
      return false;
    }

    return Base64.isBase64(str) && str.matches(BASE64_REG_EX);
  }

}