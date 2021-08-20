package ch.deeppay.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FileUtil {

  /**
   * Return true when passed folder is empty otherwise false is returnd
   * @param dir - The path to the directory
   * @return True when the folder is empty otherwise false
   * @throws IOException if an I/O error occurs when opening the directory
   */
  public static boolean isEmpty(Path dir) throws IOException {
    if (Files.isDirectory(dir)) {
      try (Stream<Path> entries = Files.list(dir)) {
        return !entries.findFirst().isPresent();
      }
    }
    return false;
  }

}