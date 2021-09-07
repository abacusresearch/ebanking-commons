package ch.deeppay.exception;

import java.net.URI;

import org.springframework.http.HttpStatus;

/**
 * Getter interface to extend DeepPayProblemType
 */
public interface DeepPayProblemTypeGetter {

  HttpStatus getHttpStatus();

  String getTitle();

  URI getUri();

}
