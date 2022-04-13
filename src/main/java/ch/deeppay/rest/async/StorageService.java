package ch.deeppay.rest.async;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import ch.deeppay.util.EncryptionUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean({AsyncConfiguration.class})
class StorageService {

  private final MinioClient minioClient;
  private final AsyncConfiguration asyncConfiguration;

  StorageService(@NonNull final AsyncConfiguration asyncConfiguration) {
    this.asyncConfiguration = asyncConfiguration;
    this.minioClient = asyncConfiguration.getMinioClient();
  }

  public String upload(String path, InputStream stream) throws Exception {
    String salt = generateSalt();

    minioClient.putObject(
        PutObjectArgs.builder()
                     .bucket(asyncConfiguration.getMinioBucketName())
                     .object(path)
                     .stream(stream, stream.available(), -1)
                     .sse(createEncryptionKey(salt))
                     .build());
    return salt;
  }


  private String generateSalt() {
    SecureRandom random = new SecureRandom();
    byte[] bytes = new byte[20];
    random.nextBytes(bytes);
    return IOUtils.toString(bytes, StandardCharsets.UTF_8.name());
  }

  public void delete(String path) throws Exception {
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(asyncConfiguration.getMinioBucketName()).object(path).build());
  }

  // Generate a new 256 bit AES key - This key must be remembered by the client.
  private ServerSideEncryptionCustomerKey createEncryptionKey(String salt) throws NoSuchAlgorithmException, InvalidKeySpecException, InvalidKeyException {
    return StringUtils.startsWith(asyncConfiguration.getMinioUrl(), "https") ?
        new ServerSideEncryptionCustomerKey(EncryptionUtil.createSecretKeySpec(asyncConfiguration.getMinioEncryptionSecret(), salt)) :
        null;
  }

}