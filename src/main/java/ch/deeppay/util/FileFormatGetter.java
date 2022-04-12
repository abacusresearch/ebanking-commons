package ch.deeppay.util;

import javax.annotation.Nonnull;

import org.springframework.lang.NonNull;

public interface FileFormatGetter {

  @NonNull
  String name();

  @Nonnull
  String getFileExtension();

  boolean isDownload();

  boolean isUpload();


}