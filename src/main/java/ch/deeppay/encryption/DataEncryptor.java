package ch.deeppay.encryption;

import javax.annotation.Nonnull;

public interface DataEncryptor {

  @Nonnull
  String encrypt(@Nonnull final String transportData);

  @Nonnull
  String decrypt(@Nonnull final String transportData);

}
