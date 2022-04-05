package ch.deeppay.util.filecollector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.util.FileUtil;
import ch.deeppay.util.ZipUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import static ch.deeppay.util.ZipUtil.isZipFile;
import static ch.deeppay.util.ZipUtil.unzip;

@Log4j2
public class FileCollector {

  public String collect(final ContentHandler handler) {
    try {
      final File tmpDirectory = Files.createTempDirectory("base64").toFile();
      try {
        handler.handle((content, fileNameProvider) -> {
          byte[] tmp = Base64.isBase64(content) ? Base64.decodeBase64(content) : content.getBytes(StandardCharsets.UTF_8);
          if (isZipFile(content)) {
            unzip(tmp, fileNameProvider.apply(tmpDirectory,true));
          } else {
            FileUtils.writeByteArrayToFile(fileNameProvider.apply(tmpDirectory,false), tmp);
          }
        });
        return FileUtil.isEmpty(tmpDirectory.toPath())
            ? StringUtils.EMPTY
            : Base64.encodeBase64String(ZipUtil.zip(tmpDirectory.toPath()));
      } finally {
        if (!FileUtils.deleteQuietly(tmpDirectory)) {
          log.error("Directory {} was not deleted", tmpDirectory);
        }
      }
    } catch (IOException e) {
      log.error("Error during content preparation", e);
      throw DeepPayProblemException.createServerErrorProblemException("Error during content preparation");
    }
  }




}