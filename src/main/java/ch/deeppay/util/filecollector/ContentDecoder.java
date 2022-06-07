package ch.deeppay.util.filecollector;

import javax.annotation.Nonnull;

public interface ContentDecoder {

  byte[] decode(@Nonnull final String content);

}
