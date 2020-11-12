package ch.deeppay.models.ebanking.server;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringFile {

  private String fileContent;
  private String format;
  private String fileName;

}