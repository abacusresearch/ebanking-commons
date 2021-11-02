package ch.deeppay.util;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import ch.deeppay.models.ebanking.server.StringFile;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.bouncycastle.util.encoders.Base64;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ZipUtilTest {

  @Test
  void testDeleteDirectory() {
    final File parentFolder = getParentTempDirectory();
    File[] files = parentFolder.listFiles((dir, name) -> name.startsWith("base64"));
    for(File f : files) {
      ZipUtil.deleteDirectory(f);
    }

    files = parentFolder.listFiles((dir, name) -> name.startsWith("base64"));
    Assertions.assertEquals(0,files.length);
  }

  @Test
  void testGetBase64Zip() throws IOException {
    StringFile file1 = new StringFile("FILE_CONTENT1", FileFormat.PAIN002.name(),"FILE_NAME1");
    StringFile file2 = new StringFile("FILE_CONTENT2", FileFormat.PAIN002.name(),"FILE_NAME2");
    String base64 = ZipUtil.getBase64Zip(Arrays.asList(file1,file2));
    Assertions.assertNotNull(base64);

    final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      ZipUtil.unzip(Base64.decode(base64), tmpDirectory);
      File[] files = tmpDirectory.listFiles();
      Assertions.assertEquals(1,files.length);

      files = files[0].listFiles();
      Assertions.assertEquals(2,files.length);
      Assertions.assertEquals("FILE_NAME1",files[0].getName());
      Assertions.assertEquals("FILE_NAME2",files[1].getName());
    }finally {
      FileUtils.deleteDirectory(tmpDirectory);
    }
  }

//  @Test
//  void testGetBase64ZipWithEmptyFilesNames() throws IOException {
//    StringFile file1 = new StringFile("FILE_CONTENT1", FileFormat.PAIN002.name(),"");
//    StringFile file2 = new StringFile("FILE_CONTENT2", FileFormat.PAIN002.name(),"");
//    String base64 = ZipUtil.getBase64Zip(Arrays.asList(file1,file2));
//    Assertions.assertNotNull(base64);
//
//    final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
//    try {
//      ZipUtil.unzip(Base64.decode(base64), tmpDirectory);
//      File[] files = tmpDirectory.listFiles();
//      Assertions.assertEquals(1,files.length);
//
//      files = files[0].listFiles();
//      Assertions.assertEquals(2,files.length);
//      Assertions.assertEquals("PAIN002_2021-11-02-11-12-24_1",files[0].getName());
//      Assertions.assertEquals("FILE_NAME2",files[1].getName());
//    }finally {
//      FileUtils.deleteDirectory(tmpDirectory);
//    }
//  }

  private File getParentTempDirectory(){
    try {
      final File tmpDirectory = Files.createTempDirectory("tmp").toFile();
      try{
        return tmpDirectory.getParentFile();
      }finally {
        FileUtils.deleteDirectory(tmpDirectory);
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }



  @Test
  void testUnzipFiles() throws URISyntaxException, IOException {
    URL resource = getClass().getResource("ZippedBase64EDoc.txt");
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
    for(File f : files){
      Assertions.assertTrue(Files.size(f.toPath()) > 1000);
    }
  }

  @Test
  void testZipFiles() throws URISyntaxException, IOException {
    URL resource = getClass().getResource("blank.pdf");
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));

    File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      File folder1 = new File(tmpDirectory, "account 1");
      assertTrue(folder1.mkdir());

      FileUtils.writeByteArrayToFile(new File(folder1, "edoc_1.pdf"),b);

      File folder2 = new File(tmpDirectory, "account 2");
      assertTrue(folder2.mkdir());
      FileUtils.writeByteArrayToFile(new File(folder2, "edoc_2.pdf"),b);
      FileUtils.writeByteArrayToFile(new File(folder2, "edoc_3.pdf"),b);

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
    URL resource = getClass().getResource("blank.pdf");
    byte[] b = IOUtils.toByteArray(Files.newInputStream(Paths.get(resource.toURI())));

    File tmpDirectory = Files.createTempDirectory("tmp").toFile();
    try {
      FileUtils.writeByteArrayToFile(new File(tmpDirectory, "camt053.txt"),b);
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
  void testZipContent() throws IOException {
    byte[] content = "CONTENT".getBytes(StandardCharsets.UTF_8);
    byte[] result = ZipUtil.zip(content);
    assertNotNull(result);
    byte[] validate = ZipUtil.unzip(result);
    Assertions.assertArrayEquals(content,validate);
  }

//  @Test
//  void testGenerateFile() throws IOException {
//    String fileName = "PAIN001.xml";
//    File file = new File(fileName);
//    file.createNewFile();
//    final File folder = Files.createTempDirectory("folder").toFile();
//    File file = File.createTempFile(FilenameUtils.getBaseName(fileName), FilenameUtils.getExtension(fileName));
//  }




}