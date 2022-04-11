package ch.deeppay.rest.async;

import ch.deeppay.util.FileFormat;

/**
 * Interface provides session information like subject claim of the jwt token or file format of the request.
 * The data are used in different threads, that means the provided data should not be binded to the current thread.
 */
public interface AsyncContextDataProvider {

  String JWT_SUBJECT = "x-jwt-subject";

  String getSubjectClaim();

  FileFormat getFormat();

}
