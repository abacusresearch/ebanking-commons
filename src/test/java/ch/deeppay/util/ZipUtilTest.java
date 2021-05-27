package ch.deeppay.util;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import ch.deeppay.models.ebanking.server.StringFile;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

 class ZipUtilTest {



  @Test
  void test() throws IOException {
    final File parentFolder = getParentTempDirectory();
    File[] files = parentFolder.listFiles((dir, name) -> name.startsWith("base64"));
    for(File f : files) {
      ZipUtil.deleteDirectory(f);
    }

    ZipUtil zipUtil = new ZipUtil();
    StringFile file1 = new StringFile("FILE_CONTENT1", FileFormat.PAIN002.name(),"FILEN_NAME1");
    StringFile file2 = new StringFile("FILE_CONTENT2", FileFormat.PAIN002.name(),"FILEN_NAME2");
    String base64 = zipUtil.getBase64Zip(Arrays.asList(file1,file2));

    files = parentFolder.listFiles((dir, name) -> name.startsWith("base64"));
    Assertions.assertEquals(0,files.length);
  }

  private File getParentTempDirectory(){
    try {
      final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
      try{
        return tmpDirectory.getParentFile();
      }finally {
        Files.delete(tmpDirectory.toPath());
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}