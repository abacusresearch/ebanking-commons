package ch.deeppay.rest.async;

import ch.deeppay.util.FileFormat;

public interface AsyncContextDataProvider {

  String getSubjectClaim();

  FileFormat getFormat();

}
