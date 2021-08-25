package ch.deeppay.util;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.apache.commons.lang.StringUtils;

public class FileUtil {

  private static final String[] charMapping = getCharMapping();

  @Nonnull
  public static String getUploadFilename(@Nullable final String fileName, @Nonnull final FileFormat fileFormat, final boolean lowercase, final int maxLength) {
    String result;
    if (StringUtils.isEmpty(fileName)) {
      result = "file";
    } else {
      result = removeExtension(fileName);
    }

    result = eliminateInvalidCharacters(result);
    if (result.length() + fileFormat.getFileExtension().length() > maxLength) {
      result = result.substring(0, maxLength - fileFormat.getFileExtension().length());
    }
    result += fileFormat.getFileExtension();

    if (lowercase) {
      result = result.toLowerCase();
    } else {
      result = result.toUpperCase();
    }

    return result;
  }

  @Nonnull
  static String removeExtension(@Nonnull final String fileName) {
    if (!fileName.isEmpty() && fileName.substring(1).contains(".")) {
      final String s = fileName.substring(1);
      return fileName.substring(0, 1) + s.substring(0, s.indexOf('.'));
    }
    return fileName;
  }

  @Nonnull
  public static String getUploadFilenameLowercase(@Nonnull final String fileName, @Nonnull final FileFormat fileFormat) {
    return getUploadFilename(fileName, fileFormat, true, 64);
  }

  @Nonnull
  public static String getUploadFilenameLowercase(@Nonnull final String fileName, @Nonnull final FileFormat fileFormat, int maxLength) {
    return getUploadFilename(fileName, fileFormat, true, maxLength);
  }

  @Nonnull
  private static String eliminateInvalidCharacters(@Nonnull final String input) {
    char[] chars = input.toCharArray();
    StringBuilder result = new StringBuilder();

    for (char c : chars) {
      if (c < 256) {
        result.append(charMapping[c]);
      } else {
        result.append('_');
      }
    }

    return result.toString();
  }

  @Nonnull
  private static String[] getCharMapping() {
    return new String[]{"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "",
                        "_", "_", "_", "_", "_", "_",
                        "+", "'", "(", ")", "_", "+", "_",
                        "-", "_", "_",
                        "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                        "_", "_", "_", "_", "_", "_", "_",
                        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                        "_", "_", "_", "_", "_", "_",
                        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                        "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_", "_",
                        "A", "A", "A", "A", "AE", "A", "AE", "C", "E", "E", "E", "E", "I", "I", "I", "I", "_", "N", "O", "O", "O", "O", "OE", "_", "_", "U", "U", "U", "UE", "Y", "_", "ss",
                        "a", "a", "a", "a", "ae", "a", "ae", "c", "e", "e", "e", "e", "i", "i", "i", "i", "_", "n", "o", "o", "o", "o", "oe", "_", "_", "u", "u", "u", "ue", "y", "_", "y"};
  }

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