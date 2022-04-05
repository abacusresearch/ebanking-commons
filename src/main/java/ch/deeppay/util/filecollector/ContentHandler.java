package ch.deeppay.util.filecollector;

import java.io.IOException;

public interface ContentHandler {

  void handle(StoreFile storeFile) throws IOException;
}