package ch.deeppay.rest.async;

import java.io.InputStream;

public interface AsyncResponseSaveHandler {

  void handleResponse(final InputStream stream);

  void handleResponse(final Throwable exception);

  void createJob();

  String getIdentifier();

}