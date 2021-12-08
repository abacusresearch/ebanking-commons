package ch.deeppay.metrics;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpHeaders;
import org.springframework.util.CollectionUtils;

import static ch.deeppay.metrics.ClientType.ABACUS_G4;
import static ch.deeppay.metrics.ClientType.ABANINJA;
import static ch.deeppay.metrics.ClientType.UNKNOWN;
import static org.apache.commons.lang.StringUtils.isNotEmpty;

public class MetricsUtil {

  static final String HTTP_HEADER_ABANINJA = "x-abaninja-info-account";
  static final String HTTP_HEADER_ABACUS = "x-abacus-info-product";
  static final String HTTP_HEADER_ABACUS_LICENSE = "x-abacus-info-license";

  public static ClientType getClientType(@Nonnull HttpServletRequest request) {
    String userAgent = request.getHeader(HttpHeaders.USER_AGENT);
    ClientType clientType = ClientType.fromUserAgent(userAgent);
    if (Objects.isNull(clientType) || clientType == UNKNOWN) {
      if (isNotEmpty(request.getHeader(HTTP_HEADER_ABANINJA))) {
        return ABANINJA;
      } else if (isNotEmpty(request.getHeader(HTTP_HEADER_ABACUS))) {
        return ABACUS_G4;
      } else {
        return UNKNOWN;
      }
    } else {
      return clientType;
    }
  }

  public static ClientType getClientType(@Nonnull HttpHeaders headers) {
    List<String> userAgents = headers.get(HttpHeaders.USER_AGENT);
    ClientType clientType = CollectionUtils.isEmpty(userAgents) ? null : ClientType.fromUserAgent(userAgents.get(0));
    if (Objects.isNull(clientType) || clientType == UNKNOWN) {
      if (headers.containsKey(HTTP_HEADER_ABANINJA)) {
        return ABANINJA;
      } else if (headers.containsKey(HTTP_HEADER_ABACUS)) {
        return ABACUS_G4;
      } else {
        return UNKNOWN;
      }
    } else {
      return clientType;
    }
  }

  public static String getClientId(@Nonnull HttpServletRequest request) {
    if (isNotEmpty(request.getHeader(HTTP_HEADER_ABANINJA))) {
      return request.getHeader(HTTP_HEADER_ABANINJA);
    } else if (isNotEmpty(request.getHeader(HTTP_HEADER_ABACUS_LICENSE))) {
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