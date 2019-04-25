package ch.deeppay.models.server;

import ch.deeppay.util.FileFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class StringFile {

  private String fileContent;
  private FileFormat format;
  private String fileName;
}
