package ch.deeppay.util.filecollector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

import ch.deeppay.exception.DeepPayProblemException;
import ch.deeppay.util.FileUtil;
import ch.deeppay.util.ZipUtil;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

import static ch.deeppay.util.ZipUtil.isZipFile;
import static ch.deeppay.util.ZipUtil.unzip;

@Log4j2
public class FileCollector {

  public String collect(final ContentHandler handler) {
    try(ByteArrayOutputStream out = new ByteArrayOutputStream()){
      collect(handler,out);
      byte[] b = out.toByteArray();
      return ArrayUtils.isEmpty(b) ? StringUtils.EMPTY : Base64.encodeBase64String(b);
    } catch (IOException e) {
      log.error("Error during content preparation", e);
      throw DeepPayProblemException.createServerErrorProblemException("Error during content preparation");
    }
  }

  public void collect(final ContentHandler handler, OutputStream out) {
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

        if(!FileUtil.isEmpty(tmpDirectory.toPath())){
          ZipUtil.zip(tmpDirectory.toPath(),out);
        }
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