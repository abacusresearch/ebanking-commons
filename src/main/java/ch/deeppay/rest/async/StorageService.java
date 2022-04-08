package ch.deeppay.rest.async;

import java.io.InputStream;

import ch.deeppay.util.EncryptionUtil;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.ServerSideEncryptionCustomerKey;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnBean({AsyncConfiguration.class,MinioClient.class})
public class StorageService {

  private final MinioClient minioClient;
  private final AsyncConfiguration asyncConfiguration;

  public StorageService(@Qualifier(AsyncConfiguration.MINIO_CLIENT_NAME) final MinioClient minioClient, final AsyncConfiguration asyncConfiguration) {
    this.minioClient = minioClient;
    this.asyncConfiguration = asyncConfiguration;
  }

  public void upload(String path, InputStream stream) throws Exception {

    // Generate a new 256 bit AES key - This key must be remembered by the client.
    ServerSideEncryptionCustomerKey ssec = null;
    if (StringUtils.startsWith(asyncConfiguration.getMinioUrl(), "https")) {
      ssec = new ServerSideEncryptionCustomerKey(EncryptionUtil.createSecretKeySpec(asyncConfiguration.getMinioEncryptionSecret(),"SALT"));
    }

    minioClient.putObject(
        PutObjectArgs.builder()
                     .bucket(asyncConfiguration.getMinioBucketName())
                     .object(path)
                     .stream(stream, stream.available(), -1)
                     .sse(ssec)
                     .build());
  }


  public void delete(String path) throws Exception {
    minioClient.removeObject(RemoveObjectArgs.builder().bucket(asyncConfiguration.getMinioBucketName()).object(path).build());
  }


}