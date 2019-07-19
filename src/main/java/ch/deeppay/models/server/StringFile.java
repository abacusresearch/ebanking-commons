package ch.deeppay.models.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringFile {

  private String fileContent;
  private String format;
  private String fileName;

}