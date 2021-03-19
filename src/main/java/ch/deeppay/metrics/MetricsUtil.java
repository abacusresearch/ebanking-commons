package ch.deeppay.metrics;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import static ch.deeppay.metrics.ClientType.ABACUS_G4;
import static ch.deeppay.metrics.ClientType.ABANINJA;
import static ch.deeppay.metrics.ClientType.POSTMAN;
import static ch.deeppay.metrics.ClientType.UNKNOWN;

public class MetricsUtil {

  private static final String HTTP_HEADER_ABANINJA = "x-abaninja-info-account";
  private static final String HTTP_HEADER_ABACUS = "x-abacus-info-product";
  private static final String HTTP_HEADER_ABACUS_LICENSE = "x-abacus-info-license";

  public static ClientType get(@Nonnull HttpServletRequest request) {
    if (StringUtils.isNotEmpty(request.getHeader(HTTP_HEADER_ABANINJA))) {
      return ABANINJA;
    } else if (StringUtils.isNotEmpty(request.getHeader(HTTP_HEADER_ABACUS))) {
      return ABACUS_G4;
    } else {
      final String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
      if (StringUtils.isNotEmpty(userAgent) && userAgent.startsWith("PostmanRuntime")) {
        return POSTMAN;
      } else {
        return UNKNOWN;
      }
    }
  }

  public static ClientType get(@Nonnull HttpHeaders headers) {
    if (headers.containsKey(HTTP_HEADER_ABANINJA)) {
      return ABANINJA;
    } else if (headers.containsKey(HTTP_HEADER_ABACUS)) {
      return ABACUS_G4;
    } else {
      List<String> values = headers.get(HttpHeaders.USER_AGENT);
      if (!CollectionUtils.isEmpty(values) && values.get(0).startsWith("PostmanRuntime")) {
        return POSTMAN;
      } else {
        return UNKNOWN;
      }
    }
  }

  public static String getClientId(@Nonnull HttpServletRequest request) {
    if (StringUtils.isNotEmpty(request.getHeader(HTTP_HEADER_ABANINJA))) {
      return request.getHeader(HTTP_HEADER_ABANINJA);
    } else if (StringUtils.isNotEmpty(request.getHeader(HTTP_HEADER_ABACUS_LICENSE))) {
      return request.getHeader(HTTP_HEADER_ABACUS_LICENSE);
    } else {
      return "";
    }
  }

  public static String getClientId(@Nonnull HttpHeaders headers) {
    List<String> list = headers.get(HTTP_HEADER_ABANINJA);
    if (!CollectionUtils.isEmpty(list)) {
      return list.get(0);
    }

    list = headers.get(HTTP_HEADER_ABACUS_LICENSE);
    if (!CollectionUtils.isEmpty(list)) {
      return list.get(0);
    }

    return "";
  }
}