package ch.deeppay.util;

import javax.annotation.Nonnull;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipInputStream;

import ch.deeppay.models.ebanking.server.StringFile;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import org.apache.commons.codec.Charsets;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

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
        for (StringFile file : files) {
          int filenumber = 0;
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
      throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Creating of zip file failed.");
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

  public boolean isZipFile(@NonNull final String fileContent) {
    return isZipFile(fileContent.getBytes(Charsets.UTF_8));
  }

  public boolean isZipFile(@NonNull final byte[] fileContent) {
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
    Date date = new Date();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    String name = file.getFileName() != null ? file.getFileName() : file.getFormat() + '_' + dateFormat.format(date) + '_' + count;
    return File.createTempFile(name, "", tmpDirectory);
  }

}