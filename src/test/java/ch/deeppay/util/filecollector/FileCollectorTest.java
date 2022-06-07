package ch.deeppay.util.filecollector;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Objects;

import ch.deeppay.util.Base64Util;
import ch.deeppay.util.ZipUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FileCollectorTest {

  private static final String CONTENT = "<Content>";

  @Test
  void testPlainContent() throws IOException {
    FileCollector collector = new FileCollector();
    String result = collector.collect(storeFile -> {
      storeFile.store(CONTENT,(parent, isZip) -> {
        Assertions.assertFalse(isZip);
        return new File(parent,"myFile");
      });
    });

    Assertions.assertTrue(Base64Util.isBase64(result));
    final File folder = Files.createTempDirectory("test").toFile();
    try {
      ZipUtil.unzip(Base64.decodeBase64(result), folder);
      File[] files = folder.listFiles();
      Assertions.assertEquals(1, files.length);
      Assertions.assertEquals(CONTENT,FileUtils.readFileToString(files[0], StandardCharsets.UTF_8));
    }finally {
      FileUtils.deleteQuietly(folder);
    }
  }


  @Test
  void testZippedContent() throws IOException {
    String content = "UEsDBBQACAgIAGNHx1QAAAAAAAAAAAAAAAAEAAAAZGF0YbNxzs8rSc0rsQMAUEsHCA22zw4LAAAACQAAAFBLAQIUABQACAgIAGNHx1QNts8OCwAAAAkAAAAEAAAAAAAAAAAAAAAAAAAAAABkYXRhUEsFBgAAAAABAAEAMgAAAD0AAAAAAA==";
    FileCollector collector = new FileCollector();
    String result = collector.collect(storeFile -> {
      storeFile.store(content,(parent, isZip) -> {
        Assertions.assertTrue(isZip);
        return parent;
      });
    });

    Assertions.assertTrue(Base64Util.isBase64(result));
    final File folder = Files.createTempDirectory("test").toFile();
    try {
      ZipUtil.unzip(Base64.decodeBase64(result), folder);
      File[] files = folder.listFiles();
      Assertions.assertEquals(1, Objects.requireNonNull(files).length);
      Assertions.assertEquals(CONTENT,FileUtils.readFileToString(files[0], StandardCharsets.UTF_8));
    }finally {
      FileUtils.deleteQuietly(folder);
    }
  }

  @Test
  void testDecoder() throws IOException {
    FileCollector collector = new FileCollector();
    String result = collector.collect(storeFile ->
      storeFile.store(CONTENT,(parent, isZip) -> {
        Assertions.assertFalse(isZip);
        return new File(parent,"myFile");
      })
    ,content1 -> CONTENT.getBytes(StandardCharsets.ISO_8859_1));

    Assertions.assertTrue(Base64Util.isBase64(result));
    final File folder = Files.createTempDirectory("test").toFile();
    try {
      ZipUtil.unzip(Base64.decodeBase64(result), folder);
      File[] files = folder.listFiles();
      Assertions.assertEquals(1, Objects.requireNonNull(files).length);
      Assertions.assertEquals(CONTENT,FileUtils.readFileToString(files[0], StandardCharsets.UTF_8));
    }finally {
      FileUtils.deleteQuietly(folder);
    }
  }

}