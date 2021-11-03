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
import java.util.Collections;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.CompressionMethod;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import static org.apache.commons.codec.binary.Base64.decodeBase64;

@SuppressWarnings("unused")
public class ZipUtil {

  public static final String DATE_TIME_FORMAT = "yyyy-MM-dd-HH-mm-ss";

  private static final Logger LOGGER = Logger.getLogger(ZipUtil.class.getName());

  public static String getBase64Zip(@NonNull final List<StringFile> files) {
    if (files.isEmpty()) {
      return StringUtils.EMPTY;
    }

    try {
      // create dir for all files
      final File tmpDirectory = Files.createTempDirectory("folder").toFile();
      try {
        int counter = 0;
        for (StringFile file : files) {
          final File current = generateFile(file, tmpDirectory, ++counter);
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
    } catch (IOException e) {
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

  public static boolean isZipFile(@Nullable final String fileContent) {
    if(StringUtils.isEmpty(fileContent)){
      return false;
    }

    return isZipFile(fileContent.getBytes(StandardCharsets.UTF_8));
  }

  public static boolean isZipFile(@Nullable final byte[] fileContent) {
    if(ArrayUtils.isEmpty(fileContent)){
      return false;
    }

    try (ByteArrayInputStream is = new ByteArrayInputStream(fileContent)) {
      return new ZipInputStream(is).getNextEntry() != null;
    } catch (IOException e) {
      return false;
    }
  }

  public static File createZipFile(@NonNull final File file) throws ZipException {
    // DO not use -> File.createTempFile -> does not work with ZipFile library
    File zipFile = new File(System.getProperty("java.io.tmpdir"), UUID.randomUUID() + ".zip");
    ZipFile myZipFile = new ZipFile(zipFile);

    ZipParameters parameters = new ZipParameters();
    parameters.setCompressionMethod(CompressionMethod.DEFLATE);
    if (file.isDirectory()) {
      myZipFile.addFolder(file, parameters);
    } else {
      myZipFile.addFile(file);
    }
    return zipFile;
  }

  @Nullable
  private static String encodeFileToString(@NonNull final String filepath) {
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

  private static void writeToFile(@NonNull final File temp, @NonNull final String fileContent) throws IOException {
    try (FileWriter file = new FileWriter(temp)) {
      try (BufferedWriter bf = new BufferedWriter(file)) {
        bf.write(fileContent);
      }
    }
  }

  public static File generateFile(@NonNull StringFile file, @NonNull final File tmpDirectory, final int count) {
    return StringUtils.isBlank(file.getFileName()) ? generateFile(file.getFormat(), tmpDirectory, count) : new File(tmpDirectory, file.getFileName());
  }

  public static File generateFile(@NonNull String fileFormat, @NonNull final File tmpDirectory, final int count) {
    return new File(tmpDirectory, generateFileName(fileFormat, count));
  }

  public static File generateFile(@NonNull final File tmpDirectory, final int count) {
    return new File(tmpDirectory, generateFileName(null, count));
  }

  public static String generateFileName(@Nullable String fileFormat, final int count) {
    SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
    StringBuilder builder = new StringBuilder();
    if (Objects.nonNull(fileFormat)) {
      builder.append(fileFormat);
      builder.append("_");
    }

    builder.append(dateFormat.format(new Date()));
    builder.append("_");
    builder.append(count);

    return builder.toString();
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

    try (ZipInputStream zipIn = new ZipInputStream(new ByteArrayInputStream(content))) {
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
          Path path = Paths.get(filePath);
          FileUtils.createParentDirectories(path.toFile());
          Files.copy(zipIn, Paths.get(filePath));
        }
        zipIn.closeEntry();
        entry = zipIn.getNextEntry();
      }
    }
  }

  /**
   * The conent of the passed folder is zipped and return's it as a byte array
   *
   * @param dir - Content of the folder that should be zipped
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
            if ("".equals(relativePath.toString())) {
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

  /**
   * Unzip does only work for a single zipped file that does not has a subfolder
   *
   * @param str zipped string
   * @return unzipped arrays of bytes
   * @throws IOException
   */
  public static byte[] unzip(final String str) {
    return isZipFile(str) ? unzip(str.getBytes(StandardCharsets.UTF_8)) : ArrayUtils.EMPTY_BYTE_ARRAY;
  }

  /**
   * Unzip does only work for a single zipped file that does not has a subfolder
   *
   * @param bytes zipped bytes
   * @return unzipped arrays of bytes
   * @throws IOException
   */
  public static byte[] unzip(final byte[] bytes) {
    if (!isZipFile(bytes)) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

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
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public static byte[] zip(final byte[] content) {
    return zip(content, "data");
  }

  /**
   * Passed byte array is zipped. The file name can contain relative sub folder (e.g. subfolder/myfile.txt)
   *
   * @param content  content that should be zipped.
   * @param fileName name of the zip entry
   * @return zipped byte array
   * @throws IOException
   */
  public static byte[] zip(final byte[] content, final String fileName) {
    return zip(Collections.singletonList(EntryData.builder().content(content).fileName(fileName).build()));
  }

  public static byte[] zip(final List<EntryData> entries) {
    if (Objects.isNull(entries) || entries.isEmpty()) {
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    try (final ByteArrayOutputStream output = new ByteArrayOutputStream()) {
      try (final ZipOutputStream zip = new ZipOutputStream(output)) {
        zip.setMethod(ZipOutputStream.DEFLATED);
        int count = 0;
        for (EntryData entryData : entries) {
          zip.putNextEntry(new ZipEntry(StringUtils.isEmpty(entryData.getFileName()) ? generateFileName(null, ++count) : entryData.getFileName()));
          zip.write(entryData.getContent());
          zip.closeEntry();
        }
      }
      return output.toByteArray();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  /**
   * The content of the entries can be zipped, base64 encoded or plain text. The content will be decoded and unzipped (if needed)
   * and packed as a single zipped base64 encoded byte array.
   *
   * @param entries list of entries
   * @return a single base 64 encoded zip file
   */
  public static String getBase64EncodedZip(final List<EntryData> entries) {
    if (Objects.isNull(entries) || entries.isEmpty()) {
      return StringUtils.EMPTY;
    }
    entries.stream().filter(e -> Base64.isBase64(e.getContent())).forEach(e -> e.setContent(decodeBase64(e.getContent())));
    entries.stream().filter(e -> isZipFile(e.getContent())).forEach(e -> e.setContent(unzip(e.getContent())));
    return Base64.encodeBase64String(zip(entries));
  }

  public static byte[] getDecodedUnzippedContent(byte[] content){
    if(ArrayUtils.isEmpty(content)){
      return ArrayUtils.EMPTY_BYTE_ARRAY;
    }

    byte[] b = Base64.isBase64(content) ? Base64.decodeBase64(content) : content;
    return isZipFile(b) ? unzip(b) : b;
  }

  @Builder
  @AllArgsConstructor
  @Data
  public static class EntryData {

    private final String fileName;
    private byte[] content;
  }


}