package ch.deeppay.rest.async;

import javax.crypto.KeyGenerator;
import java.io.InputStream;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

  private final MinioClient minioClient;
  private final AsyncConfiguration asyncConfiguration;

  public StorageService(final MinioClient minioClient, final AsyncConfiguration asyncConfiguration) {
    this.minioClient = minioClient;
    this.asyncConfiguration = asyncConfiguration;
  }

  public void upload(String path, InputStream stream) throws Exception {

//      headers.put("Content-Type", "application/octet-stream");

    // Generate a new 256 bit AES key - This key must be remembered by the client.
    ServerSideEncryptionCustomerKey ssec = null;
    if (StringUtils.startsWith(asyncConfiguration.getMinioUrl(), "https")) {
      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
      keyGen.init(256);
      ssec = new ServerSideEncryptionCustomerKey(keyGen.generateKey());
    }

    minioClient.putObject(
        PutObjectArgs.builder()
                     .bucket(asyncConfiguration.getMinioBucketName())
                     .object(path)
                     .stream(stream, 0, -1)
                     .sse(ssec)
                     .build());
  }


  public void delete(String path) throws Exception {
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(asyncConfiguration.getMinioBucketName()).object(path).build());
  }


}