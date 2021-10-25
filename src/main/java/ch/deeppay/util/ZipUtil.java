package ch.deeppay.util;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.models.ebanking.server.StringFile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@SuppressWarnings("unused")
public class ZipUtil {

  private static final Logger LOGGER = Logger.getLogger(ZipUtil.class.getName());

  public String getBase64Zip(@NonNull final List<StringFile> files) {
    if (files.isEmpty()) {
      return StringUtils.EMPTY;
    }

    try {
      // create dir for all files
      final File tmpDirectory = Files.createTempDirectory("base64").toFile();
      try {
        int filenumber = 0;
        for (StringFile file : files) {
          final File current = generateFile(file, tmpDirectory, ++filenumber);
          writeToFile(current, file.getFileContent());
        }
        final File zipFile = createZipFile(tmpDirectory);

        final String result = encodeFileToString(zipFile.getPath());
        if (Objects.isNull(result)) {
          return StringUtils.EMPTY;
        }
        return result;
      } finally {
        deleteDirectory(tmpDirectory);
      }
    } catch (ZipException | IOException e) {
      LOGGER.warning(e.getMessage());
      throw DeepPayProblemException.createServerErrorProblemException("Creating of zip file failed.");
    }
  }

  static void deleteDirectory(@Nonnull File toBeDeleted) {
    File[] allContents = toBeDeleted.listFiles();
    if (Objects.nonNull(allContents)) {
      for (File file : allContents) {
        deleteDirectory(file);
      }
    }

    try {
      Files.delete(toBeDeleted.toPath());
    } catch (IOException e) {
      LOGGER.log(Level.WARNING, "Could not delete file", e);
    }
  }

  public static boolean isZipFile(@NonNull final String fileContent) {
    return isZipFile(fileContent.getBytes(Charsets.UTF_8));
  }

  public static boolean isZipFile(@NonNull final byte[] fileContent) {
    try (ByteArrayInputStream is = new ByteArrayInputStream(fileContent)) {
      return new ZipInputStream(is).getNextEntry() != null;
    } catch (IOException e) {
      return false;
    }
  }

  private File createZipFile(@NonNull final File tmpDirectory) throws ZipException {
    // DO not use -> File.createTempFile -> does not work with ZipFile library
    File zipFile = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID() + ".zip");
    ZipFile myZipFile = new ZipFile(zipFile);

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
    myZipFile.addFolder(tmpDirectory, parameters);
    return zipFile;
  }

  @Nullable
  private String encodeFileToString(@NonNull final String filepath) {
    final File originalFile = new File(filepath);
    String encodedBase64 = null;
    try {
      try (FileInputStream fileInputStreamReader = new FileInputStream(originalFile)) {
        byte[] bytes = new byte[(int) originalFile.length()];
        fileInputStreamReader.read(bytes);
        encodedBase64 = new String(Base64.encodeBase64(bytes), StandardCharsets.UTF_8);
      }
    } catch (IOException e) {
      LOGGER.warning("cannot encode zip file! " + filepath);
    }

    return encodedBase64;
  }

  private void writeToFile(@NonNull final File temp, @NonNull final String fileContent) throws IOException {
    try (FileWriter file = new FileWriter(temp)) {
      try (BufferedWriter bf = new BufferedWriter(file)) {
        bf.write(fileContent);
      }
    }
  }

  private File generateFile(@NonNull StringFile file,
                            @NonNull final File tmpDirectory,
                            final int count) throws IOException {
    return Objects.nonNull(file.getFileName())
        ? File.createTempFile(file.getFileName(), "", tmpDirectory)
        : generateFile(file.getFormat(), tmpDirectory, count);
  }

  public static File generateFile(@NonNull String fileFormat,
                                  @NonNull final File tmpDirectory,
                                  final int count) throws IOException {
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String name = fileFormat + '_' + dateFormat.format(date) + '_' + count;
    return File.createTempFile(name, "", tmpDirectory);
  }


  /**
   * Extracts a zip file specified by the content byte array to a directory specified by
   * destDirectory (will be created if does not exists)
   *
   * @param content       - Zipped file as a byte array
   * @param destDirectory - Destination directory where the unzipped content will be placed
   * @throws IOException
   */
  public static void unzip(@Nonnull final byte[] content, @Nonnull final File destDirectory) throws IOException {
    if (!destDirectory.exists() && !destDirectory.mkdir()) {
      throw new RuntimeException(String.format("Failed to create folder %s", destDirectory));
    }

    ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(content));
    ZipEntry entry = zipIn.getNextEntry();
    // iterates over entries in the zip file
    while (entry != null) {
      String filePath = destDirectory + File.separator + entry.getName();
      if (entry.isDirectory()) {
        // if the entry is a directory, make the directory
        File dir = new File(filePath);
        if (!dir.exists() && !dir.mkdir()) {
          throw new RuntimeException(String.format("Failed to create folder %s", destDirectory));
        }
      } else {
        // if the entry is a file, extracts it
        Files.copy(zipIn, Paths.get(filePath));
      }
      zipIn.closeEntry();
      entry = zipIn.getNextEntry();
    }
    zipIn.close();
  }

  /**
   * Zip a passed folder and return's it as a byte array
   *
   * @param dir - Folder that should be zipped
   * @return byte array of the zipped folder
   * @throws IOException
   */
  public static byte[] zip(@NonNull final Path dir) throws IOException {
    try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
      try (ZipOutputStream zs = new ZipOutputStream(out)) {
        List<Path> paths = Files.walk(dir).collect(Collectors.toList());
        for (Path path : paths) {
          Path relativePath = dir.relativize(path);

          if (Files.isDirectory(path)) {
            //skip root path element. php does not ignore it
            if("".equals(relativePath.toString())){
              continue;
            }

            zs.putNextEntry(new ZipEntry(relativePath + "/"));
          } else {
            ZipEntry zipEntry = new ZipEntry(relativePath.toString());
            zs.putNextEntry(zipEntry);
            Files.copy(path, zs);
            zs.closeEntry();
          }
        }
      }
      return out.toByteArray();
    }
  }

  public static byte[] unzip(final byte[] bytes) throws IOException {
    final byte[] buffer = new byte[1024];
    try (final ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes))) {
      try (final ByteArrayOutputStream out = new ByteArrayOutputStream(bytes.length)) {
        int numberOfBytes;
        if (zip.getNextEntry() != null) {
          while ((numberOfBytes = zip.read(buffer)) != -1) {
            out.write(buffer, 0, numberOfBytes);
          }
        }
        return out.toByteArray();
      }
    }
  }

  public static byte[] zip(final byte[] bytes) throws IOException {
    try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      try (final ZipOutputStream zip = new ZipOutputStream(output)) {
        zip.setMethod(ZipOutputStream.DEFLATED);
        final ZipEntry entry = new ZipEntry("Content");
        zip.putNextEntry(entry);
        zip.write(bytes);
        zip.closeEntry();
      }
      return output.toByteArray();
    }
  }

}