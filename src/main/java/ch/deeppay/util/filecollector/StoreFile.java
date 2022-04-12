package ch.deeppay.util.filecollector;

import java.io.File;
import java.io.IOException;
import java.util.function.BiFunction;

public interface StoreFile {

  /**
   * Implementation of this interface stores the content into a file. The filename is provided by  the fileNameProvider.
   * @param content unzipped content
   * @param fileNameProvider the first file parameter contains the parent folder and the second boolean parameter indicates if the filename is for a unzipped folder or a single file.
   * @throws IOException
   */
  void store(String content, BiFunction<File ,Boolean, File> fileNameProvider) throws IOException;

}
