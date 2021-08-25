package ch.deeppay.spring.logging;

import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.zalando.logbook.Correlation;
import org.zalando.logbook.HttpLogWriter;
import org.zalando.logbook.Precorrelation;

@Log4j2
public class CustomizedHttpLogWriter implements HttpLogWriter {

  public static final String SUPPORT = "SUPPORT";
  public static final Marker SUPPORT_MARKER = MarkerManager.getMarker(SUPPORT);

  @Override
  public boolean isActive() {
    return log.isInfoEnabled();
  }

  @Override
  public void write(final Precorrelation precorrelation, final String request) {
    log.info(SUPPORT_MARKER,request);
  }

  @Override
  public void write(final Correlation correlation, final String response) {
    log.info(SUPPORT_MARKER,response);
  }

}