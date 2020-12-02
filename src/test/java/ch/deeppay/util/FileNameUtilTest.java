package ch.deeppay.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FileNameUtilTest {

  @Test
  void getUploadFilename() {
    final String tooLongFileName = "qwertzuiopasdfghjklyxcvbnm.asdf";
    final String uploadFilename = FileNameUtil.getUploadFilename(tooLongFileName, FileFormat.DTA, false, 24);
    Assertions.assertEquals(24, uploadFilename.length());
    Assertions.assertTrue(uploadFilename.contains(FileFormat.DTA.getFileExtension().toUpperCase()));
  }

  @Test
  void removeExtension_none() {
    final String name = FileNameUtil.removeExtension("abc");
    Assertions.assertEquals("abc", name);
  }

  @Test
  void removeExtension_oneDot() {
    final String name = FileNameUtil.removeExtension("abc.c54.xml");
    Assertions.assertEquals("abc", name);
  }

  @Test
  void removeExtension_twoDots() {
    final String name = FileNameUtil.removeExtension("abc.c54.xml");
    Assertions.assertEquals("abc", name);
  }

  @Test
  void removeExtension_hiddenOneDot() {
    final String name = FileNameUtil.removeExtension(".abc.xml");
    Assertions.assertEquals(".abc", name);
  }

  @Test
  void removeExtension_hidden() {
    final String name = FileNameUtil.removeExtension(".abc");
    Assertions.assertEquals(".abc", name);
  }

}
