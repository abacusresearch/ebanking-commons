package ch.deeppay.sentry;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

class EventLevelConditionOnProperty implements Condition {

  static final String EVENT_LEVEL_OFF = "OFF";

  @Override
  public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
    String value = context.getEnvironment().getProperty("sentry.exception.resolver.event.level");
    return StringUtils.isEmpty(value) || !"false".equalsIgnoreCase(value);//spring converts OFF value into false
  }
}