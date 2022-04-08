package ch.deeppay.rest.async;

import ch.deeppay.util.FileFormat;

public interface AsyncContextDataProvider {

  String JWT_SUBJECT = "x-jwt-subject";

  String getSubjectClaim();

  FileFormat getFormat();

}
