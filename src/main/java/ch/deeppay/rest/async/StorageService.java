package ch.deeppay.rest.async;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

@Service
public class StorageService {

  private final MinioClient minioClient;
  private final AsyncConfiguration asyncConfiguration;

  public StorageService(final MinioClient minioClient, final AsyncConfiguration asyncConfiguration) {
    this.minioClient = minioClient;
    this.asyncConfiguration = asyncConfiguration;
  }

  public void upload(String path, byte[] content) throws Exception {

    try (ByteArrayInputStream stream = new ByteArrayInputStream(content)) {
      Map<String, String> headers = new HashMap<>();
//      headers.put("Content-Type", "application/octet-stream");

      // Generate a new 256 bit AES key - This key must be remembered by the client.
//      KeyGenerator keyGen = KeyGenerator.getInstance("AES");
//      keyGen.init(256);
//      ServerSideEncryptionCustomerKey ssec = new ServerSideEncryptionCustomerKey(keyGen.generateKey());
      minioClient.putObject(
          PutObjectArgs.builder().headers(headers).bucket(asyncConfiguration.getMinioBucketName()).object(path).stream(
                           stream, stream.available(), -1)
                       .build());
    }
  }

  public void delete(String path) throws Exception {
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(asyncConfiguration.getMinioBucketName()).object(path).build());
  }

  public byte[] download(String path) throws Exception {
    InputStream stream =
        minioClient.getObject(
            GetObjectArgs.builder().bucket(asyncConfiguration.getMinioBucketName()).object(path).build());
    return IOUtils.toByteArray(stream);
  }


}