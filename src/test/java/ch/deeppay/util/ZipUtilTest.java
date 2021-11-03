package ch.deeppay.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import ch.deeppay.models.ebanking.server.StringFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.ArrayUtils;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static ch.deeppay.util.ZipUtil.EntryData.builder;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZipUtilTest {

  @Test
  void testDeleteDirectory() {
    final File parentFolder = getParentTempDirectory();
    File[] files = requireNonNull(parentFolder.listFiles((dir, name) -> name.startsWith("base64")));
    for (File f : files) {
      ZipUtil.deleteDirectory(f);
    }

    files = parentFolder.listFiles((dir, name) -> name.startsWith("base64"));
    Assertions.assertEquals(0, files.length);
  }

  @Test
  void testGetBase64Zip() throws IOException {
    StringFile file1 = new StringFile("FILE_CONTENT1", FileFormat.PAIN002.name(), "FILE_NAME1");
    StringFile file2 = new StringFile("FILE_CONTENT2", FileFormat.PAIN002.name(), "FILE_NAME2");
    String base64 = ZipUtil.getBase64Zip(Arrays.asList(file1, file2));
    Assertions.assertNotNull(base64);

    final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(Base64.decode(base64), tmpDirectory);
      File[] files = requireNonNull(tmpDirectory.listFiles());
      Assertions.assertEquals(1, files.length);

      files = files[0].listFiles();
      Assertions.assertEquals(2, files.length);
      Assertions.assertEquals("FILE_NAME1", files[0].getName());
      Assertions.assertEquals("FILE_NAME2", files[1].getName());
    } finally {
      FileUtils.deleteDirectory(tmpDirectory);
    }
  }

  @Test
  void testGetBase64ZipWithEmptyFilesNames() throws IOException {
    StringFile file1 = new StringFile("FILE_CONTENT1", FileFormat.PAIN002.name(), "");
    StringFile file2 = new StringFile("FILE_CONTENT2", FileFormat.PAIN002.name(), "");
    String base64 = ZipUtil.getBase64Zip(Arrays.asList(file1, file2));
    Assertions.assertNotNull(base64);

    final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(Base64.decode(base64), tmpDirectory);
      File[] files = requireNonNull(tmpDirectory.listFiles());
      Assertions.assertEquals(1, files.length);

      files = requireNonNull(files[0].listFiles());
      Assertions.assertEquals(2, files.length);

      Date date = new Date();
      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

      Assertions.assertEquals(String.format("PAIN002_%s_1", dateFormat.format(date)), files[0].getName());
      Assertions.assertEquals(String.format("PAIN002_%s_2", dateFormat.format(date)), files[1].getName());
    } finally {
      FileUtils.deleteDirectory(tmpDirectory);
    }
  }

  private File getParentTempDirectory() {
    try {
      final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
      try {
        return tmpDirectory.getParentFile();
      } finally {
        FileUtils.deleteDirectory(tmpDirectory);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  void testUnzipFiles() throws URISyntaxException, IOException {
    URL resource = requireNonNull(getClass().getResource("ZippedBase64EDoc.txt"));
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));

    final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(Base64.decode(b), tmpDirectory);

      File[] files = tmpDirectory.listFiles();
      assertEquals(1, requireNonNull(files).length);

      File[] subFolder = files[0].listFiles();
      assertEquals(2, requireNonNull(subFolder).length);

      validateFileSize(subFolder);
    } finally {
      assertTrue(FileUtils.deleteQuietly(tmpDirectory));
    }
  }

  private void validateFileSize(File[] files) throws IOException {
    for (File f : files) {
      Assertions.assertTrue(Files.size(f.toPath()) > 1000);
    }
  }

  @Test
  void testZipFiles() throws URISyntaxException, IOException {
    URL resource = requireNonNull(getClass().getResource("blank.pdf"));
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));

    File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      File folder1 = new File(tmpDirectory, "account 1");
      assertTrue(folder1.mkdir());

      FileUtils.writeByteArrayToFile(new File(folder1, "edoc_1.pdf"), b);

      File folder2 = new File(tmpDirectory, "account 2");
      assertTrue(folder2.mkdir());
      FileUtils.writeByteArrayToFile(new File(folder2, "edoc_2.pdf"), b);
      FileUtils.writeByteArrayToFile(new File(folder2, "edoc_3.pdf"), b);

      byte[] zippedFolder = ZipUtil.zip(tmpDirectory.toPath());

      //validate zipped file
      final File folder = Files.createTempDirectory("folder").toFile();
      try {
        ZipUtil.unzip(zippedFolder, folder);
        File[] files = folder.listFiles();
        assertEquals(2, requireNonNull(files).length);
        assertEquals(1, requireNonNull(files[0].listFiles()).length);
        validateFileSize(requireNonNull(files[0].listFiles()));
        assertEquals(2, requireNonNull(files[1].listFiles()).length);
        validateFileSize(requireNonNull(files[1].listFiles()));
      } finally {
        assertTrue(FileUtils.deleteQuietly(folder));
      }
    } finally {
      assertTrue(FileUtils.deleteQuietly(tmpDirectory));
    }
  }

  @Test
  void testZipSingleFile() throws URISyntaxException, IOException {
    URL resource = requireNonNull(getClass().getResource("blank.pdf"));
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));

    File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      FileUtils.writeByteArrayToFile(new File(tmpDirectory, "camt053.txt"), b);
      byte[] zippedFolder = ZipUtil.zip(tmpDirectory.toPath());

      //validate zipped file
      final File folder = Files.createTempDirectory("folder").toFile();
      try {
        ZipUtil.unzip(zippedFolder, folder);
        File[] files = folder.listFiles();
        assertEquals(1, requireNonNull(files).length);
      } finally {
        assertTrue(FileUtils.deleteQuietly(folder));
      }
    } finally {
      assertTrue(FileUtils.deleteQuietly(tmpDirectory));
    }
  }

  @Test
  void testZipContent() {
    byte[] content = "CONTENT".getBytes(UTF_8);
    byte[] result = ZipUtil.zip(content);
    assertNotNull(result);
    byte[] validate = ZipUtil.unzip(result);
    Assertions.assertArrayEquals(content, validate);
  }

  @Test
  void testZipWithFileName() throws IOException {
    byte[] content = "CONTENT".getBytes(UTF_8);
    byte[] result = ZipUtil.zip(content, "MyFile.txt");
    assertNotNull(result);

    final File folder = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(result, folder);
      File[] files = folder.listFiles();
      assertEquals(1, requireNonNull(files).length);
      assertEquals("MyFile.txt", files[0].getName());
    } finally {
      FileUtils.deleteDirectory(folder);
    }
  }

  @Test
  void testZipWithSubFolderAndSingleFile() throws IOException {
    byte[] content = "CONTENT".getBytes(UTF_8);
    byte[] result = ZipUtil.zip(content, "subfolder1234/MyFile.txt");
    assertNotNull(result);

    final File folder = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(result, folder);
      File[] files = folder.listFiles();
      assertEquals(1, requireNonNull(files).length);
      assertEquals("subfolder1234", files[0].getName());

      files = files[0].listFiles();
      assertEquals(1, requireNonNull(files).length);
      assertEquals("MyFile.txt", files[0].getName());
    } finally {
      FileUtils.deleteDirectory(folder);
    }
  }


  @Test
  void testZipWithSubFolderAndFiles() throws IOException {
    byte[] result = ZipUtil.zip(Arrays.asList(builder()
                                                  .content("CONTENT_1".getBytes(UTF_8))
                                                  .fileName("subfolder1234/MyFile1.txt")
                                                  .build(),
                                              builder()
                                                  .content("CONTENT_12".getBytes(UTF_8))
                                                  .fileName("subfolder1234/MyFile2.txt")
                                                  .build()));
    assertNotNull(result);

    final File folder = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(result, folder);
      File[] files = folder.listFiles();
      assertEquals(1, requireNonNull(files).length);
      assertEquals("subfolder1234", files[0].getName());

      files = files[0].listFiles();
      assertEquals(2, requireNonNull(files).length);
      assertEquals("MyFile1.txt", files[0].getName());
      assertEquals("MyFile2.txt", files[1].getName());
    } finally {
      FileUtils.deleteDirectory(folder);
    }
  }

  @Test
  void testZipWithoutFileName() throws IOException {
    byte[] result = ZipUtil.zip(Arrays.asList(builder()
                                                  .content("CONTENT_1".getBytes(UTF_8))
                                                  .build(),
                                              builder()
                                                  .content("CONTENT_12".getBytes(UTF_8))
                                                  .build()));
    assertNotNull(result);

    final File folder = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(result, folder);
      File[] files = folder.listFiles();
      assertEquals(2, requireNonNull(files).length);

      assertTrue(files[0].getName().endsWith("_1"));
      assertTrue(files[1].getName().endsWith("_2"));
    } finally {
      FileUtils.deleteDirectory(folder);
    }
  }

  @Test
  void testBase64EncodedZipWithMixedContent() throws IOException {
    String result = ZipUtil.getBase64EncodedZip(Arrays.asList(builder()
                                                                  .content(Base64.encode("CONTENT_1".getBytes(UTF_8)))
                                                                  .fileName("MyFile1.txt")
                                                                  .build(),
                                                              builder()
                                                                  .content(ZipUtil.zip("CONTENT_12".getBytes(UTF_8)))
                                                                  .fileName("MyFile2.txt")
                                                                  .build()));
    final File folder = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(Base64.decode(result), folder);
      File[] files = folder.listFiles();
      assertEquals(2, requireNonNull(files).length);

      assertEquals("MyFile1.txt", files[0].getName());
      assertEquals("CONTENT_1", FileUtils.readFileToString(files[0], UTF_8));
      assertEquals("MyFile2.txt", files[1].getName());
      assertEquals("CONTENT_12", FileUtils.readFileToString(files[1], UTF_8));
    } finally {
      FileUtils.deleteDirectory(folder);
    }
  }

  @Test
  void testIsZipFailed() {
    Assertions.assertFalse(ZipUtil.isZipFile((String) null));
    Assertions.assertFalse(ZipUtil.isZipFile((byte[]) null));
    Assertions.assertFalse(ZipUtil.isZipFile("zip"));
    Assertions.assertFalse(ZipUtil.isZipFile("zip".getBytes(UTF_8)));
  }

  @Test
  void testIsZip() throws URISyntaxException, IOException {
    URL resource = requireNonNull(getClass().getResource("TestFile.zip"));
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));
    Assertions.assertTrue(ZipUtil.isZipFile(b));
  }

  @Test
  void testGetDecodedUnzippedContentWithPlainStr(){
    String content = "<TEST></TEST>";
    Assertions.assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ZipUtil.getDecodedUnzippedContent(null));
    Assertions.assertEquals(content, new String(ZipUtil.getDecodedUnzippedContent(content.getBytes(UTF_8)), UTF_8));
  }

  @Test
  void testGetDecodedUnzippedContentWithBase64Str(){
    String content = "<TEST></TEST>";
    Assertions.assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ZipUtil.getDecodedUnzippedContent(null));
    Assertions.assertEquals(content, new String(ZipUtil.getDecodedUnzippedContent(Base64.encode(content.getBytes(UTF_8))), UTF_8));
  }

  @Test
  void testGetDecodedUnzippedContentZip(){
    String content = "<TEST></TEST>";
    Assertions.assertEquals(ArrayUtils.EMPTY_BYTE_ARRAY, ZipUtil.getDecodedUnzippedContent(null));
    Assertions.assertEquals(content, new String(ZipUtil.getDecodedUnzippedContent(ZipUtil.zip(content.getBytes(UTF_8))), UTF_8));
  }

}