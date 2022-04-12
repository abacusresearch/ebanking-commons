package ch.deeppay.rest.async;

import java.io.InputStream;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseData {

  private String identifier;
  private Throwable exception;
  private InputStream response;

}